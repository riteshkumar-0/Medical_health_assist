package com.medical.config;

import com.medical.entity.Product;
import com.medical.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

        private final ProductRepository productRepository;

        @Override
        public void run(String... args) {
                if (productRepository.count() > 0) {
                        log.info("Products already exist. Skipping data initialization.");
                        return;
                }

                log.info("Initializing sample product data...");

                List<Product> products = List.of(
                                Product.builder()
                                                .productId("HM-001")
                                                .productName("VitalSign Pro Monitor")
                                                .description("An advanced multi-parameter patient monitor designed for continuous vital sign tracking in clinical and home settings. Monitors heart rate, blood pressure, SpO2, temperature, and respiratory rate with medical-grade accuracy.")
                                                .category("Vital Signs Monitoring")
                                                .keyFeatures("Real-time ECG monitoring, Non-invasive blood pressure (NIBP), Pulse oximetry (SpO2), Body temperature tracking, Respiratory rate detection, Wireless data transmission, 7-inch touchscreen display, 72-hour battery life, FDA-cleared, HL7 FHIR integration")
                                                .build(),

                                Product.builder()
                                                .productId("HM-002")
                                                .productName("GlucoTrack Continuous Monitor")
                                                .description("A state-of-the-art continuous glucose monitoring system that provides real-time glucose readings every 5 minutes. Uses a minimally invasive sensor for up to 14 days of uninterrupted monitoring with smartphone integration.")
                                                .category("Glucose Monitoring")
                                                .keyFeatures("Continuous glucose readings every 5 minutes, 14-day sensor lifespan, Smartphone app integration, Predictive glucose alerts, Trend analysis and reporting, Water-resistant sensor, No fingerstick calibration needed, Cloud data sharing with healthcare providers, Customizable alert thresholds, Compatible with insulin pumps")
                                                .build(),

                                Product.builder()
                                                .productId("HM-003")
                                                .productName("CardioRhythm Smart ECG")
                                                .description("A portable 12-lead ECG device that enables medical-grade electrocardiogram recording from anywhere. Features AI-powered arrhythmia detection and instant interpretation for both clinical and personal use.")
                                                .category("Cardiac Monitoring")
                                                .keyFeatures("12-lead ECG recording, AI-powered arrhythmia detection, Atrial fibrillation screening, Heart rate variability analysis, Instant ECG interpretation, PDF report generation, Bluetooth connectivity, 30-second recording time, Cloud storage for ECG history, Physician dashboard access")
                                                .build(),

                                Product.builder()
                                                .productId("HM-004")
                                                .productName("SleepWell Analytics Band")
                                                .description("An advanced sleep monitoring wearable that tracks sleep stages, breathing patterns, and sleep quality using clinical-grade sensors. Provides personalized insights and recommendations to improve sleep health.")
                                                .category("Sleep Monitoring")
                                                .keyFeatures("Sleep stage detection (REM, Deep, Light), Blood oxygen monitoring during sleep, Snoring detection and analysis, Sleep apnea screening, Heart rate tracking overnight, Sleep quality scoring, Personalized sleep coaching, Smart alarm with optimal wake time, 7-day battery life, Skin temperature monitoring")
                                                .build(),

                                Product.builder()
                                                .productId("HM-005")
                                                .productName("PulseOx Fingertip Scanner")
                                                .description("A clinical-grade fingertip pulse oximeter that provides instant and accurate blood oxygen saturation and pulse rate readings. Designed for both professional healthcare settings and home monitoring.")
                                                .category("Vital Signs Monitoring")
                                                .keyFeatures("SpO2 accuracy ±2%, Pulse rate monitoring, Perfusion index measurement, Plethysmograph waveform display, OLED dual-color display, Auto power-off, Low battery indicator, Suitable for ages 4+, Lanyard included, 30-hour battery life")
                                                .build(),

                                Product.builder()
                                                .productId("HM-006")
                                                .productName("NeuroFocus EEG Headband")
                                                .description("A consumer-friendly EEG headband that monitors brain wave activity to assess focus, stress, and cognitive performance. Used for meditation guidance, neurofeedback training, and cognitive health tracking.")
                                                .category("Neurological Monitoring")
                                                .keyFeatures("4-channel EEG sensor array, Real-time brainwave analysis, Focus and attention scoring, Stress level monitoring, Guided meditation modes, Neurofeedback training programs, Bluetooth 5.0, Companion mobile app, 10-hour battery life, Lightweight ergonomic design")
                                                .build());

                productRepository.saveAll(products);
                log.info("Successfully initialized {} sample products.", products.size());
        }
}
