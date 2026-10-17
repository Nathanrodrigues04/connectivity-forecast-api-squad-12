
package com.brazcubas.apsii.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QualityClassifierTest {

    private final QualityClassifier classifier = new QualityClassifier();

    // TESTES EXISTENTES

    @Test
    void classifiesGoodConnection() {
        assertEquals("GOOD", classifier.classify(30.0, 0.0));
    }

    @Test
    void classifiesModeratePacketLoss() {
        assertEquals("MODERATE", classifier.classify(40.0, 1.5));
    }

    @Test
    void worseMetricDeterminesQuality() {
        assertEquals("UNSTABLE", classifier.classify(20.0, 5.0));
    }

    
    @Test
    void classifiesExactly60MsAsModerate() {
        // Arrange
        double rtt = 60.0;
        double packetLoss = 0.0;

        // Act
        String result = classifier.classify(rtt, packetLoss);

        // Assert
        assertEquals("MODERATE", result);
    }

    @Test
    void rejectsNegativeRtt() {
        // Arrange
        double rtt = -10.0;
        double packetLoss = 0.0;

        // Act e Assert
        assertThrows(
            IllegalArgumentException.class,
            () -> classifier.classify(rtt, packetLoss)
        );
    }    
}