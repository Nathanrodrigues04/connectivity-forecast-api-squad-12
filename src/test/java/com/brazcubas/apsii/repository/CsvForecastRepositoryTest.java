package com.brazcubas.apsii.repository;

import com.brazcubas.apsii.model.Forecast;
import com.brazcubas.apsii.model.PredictionModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CsvForecastRepositoryTest {
    private CsvForecastRepository repository;

    @BeforeEach
    void setUp() {
        // Arrange: catálogo de modelos conhecidos, sem depender de arquivo externo.
        ModelRepository models = mock(ModelRepository.class);
        when(models.findAll()).thenReturn(List.of(model("model-a"), model("model-b")));
        repository = new CsvForecastRepository(
                new DefaultResourceLoader(), models,
                "classpath:data-test/predictions-11-2.csv");
        repository.load();
    }

    @Test
    void blankConfidenceIsLoadedAsNull() {
        // Act: consulta pública do repositório.
        Forecast forecast = byId("mock-a-2");
        // Assert
        assertNull(forecast.modelConfidence());
    }

    @Test
    void zeroConfidenceIsAccepted() {
        assertEquals(0.0, byId("mock-a-3").modelConfidence());
    }

    @Test
    void oneConfidenceIsAccepted() {
        assertEquals(1.0, byId("mock-a-4").modelConfidence());
    }

    @Test
    void invalidConfidenceRowsDoNotAppearInPublicQueries() {
        // Arrange: fixture com 9 registros: 5 válidos, 3 confianças inválidas,
        // e 1 registro cujo modelo não existe no catálogo.
        // Act
        List<Forecast> all = repository.findAll();
        List<Forecast> timeline = repository.findByModelAndProbe("model-a", 900001);
        // Assert: conferir também os IDs para não aceitar falsos positivos na contagem.
        assertEquals(5, all.size());
        assertEquals(4, timeline.size());
        assertEquals(List.of("mock-a-1", "mock-a-2", "mock-a-3", "mock-a-4"),
                timeline.stream().map(Forecast::predictionId).toList());
        assertEquals(Set.of("mock-a-1", "mock-a-2", "mock-a-3", "mock-a-4", "mock-b-1"),
                all.stream().map(Forecast::predictionId).collect(Collectors.toSet()));
    }

    private Forecast byId(String id) {
        return repository.findAll().stream()
                .filter(forecast -> forecast.predictionId().equals(id))
                .findFirst().orElseThrow();
    }

    private static PredictionModel model(String id) {
        return new PredictionModel(id, "Modelo " + id, "Descrição de teste", "Grupo",
                "Algoritmo", "1.0", true, null);
    }
}
