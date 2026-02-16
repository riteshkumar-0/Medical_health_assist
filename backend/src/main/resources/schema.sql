-- ============================================================
-- Medical Information Assistant - Database Schema & Sample Data
-- ============================================================

-- Step 1: Create the database
CREATE DATABASE IF NOT EXISTS medical_assistant_db;
USE medical_assistant_db;

-- Step 2: Create the products table
CREATE TABLE IF NOT EXISTS products (
    product_id   VARCHAR(10)  NOT NULL PRIMARY KEY,
    product_name VARCHAR(150) NOT NULL,
    description  TEXT,
    category     VARCHAR(100),
    key_features TEXT
);

-- Step 3: Insert sample health monitoring product data (6 products, 5 categories)
INSERT INTO products (product_id, product_name, description, category, key_features) VALUES

('HM-001', 'VitalSign Pro Monitor',
 'An advanced multi-parameter patient monitor designed for continuous vital sign tracking in clinical and home settings. Monitors heart rate, blood pressure, SpO2, temperature, and respiratory rate with medical-grade accuracy.',
 'Vital Signs Monitoring',
 'Real-time ECG monitoring, Non-invasive blood pressure (NIBP), Pulse oximetry (SpO2), Body temperature tracking, Respiratory rate detection, Wireless data transmission, 7-inch touchscreen display, 72-hour battery life, FDA-cleared, HL7 FHIR integration'),

('HM-002', 'GlucoTrack Continuous Monitor',
 'A state-of-the-art continuous glucose monitoring system that provides real-time glucose readings every 5 minutes. Uses a minimally invasive sensor for up to 14 days of uninterrupted monitoring with smartphone integration.',
 'Glucose Monitoring',
 'Continuous glucose readings every 5 minutes, 14-day sensor lifespan, Smartphone app integration, Predictive glucose alerts, Trend analysis and reporting, Water-resistant sensor, No fingerstick calibration needed, Cloud data sharing with healthcare providers, Customizable alert thresholds, Compatible with insulin pumps'),

('HM-003', 'CardioRhythm Smart ECG',
 'A portable 12-lead ECG device that enables medical-grade electrocardiogram recording from anywhere. Features AI-powered arrhythmia detection and instant interpretation for both clinical and personal use.',
 'Cardiac Monitoring',
 '12-lead ECG recording, AI-powered arrhythmia detection, Atrial fibrillation screening, Heart rate variability analysis, Instant ECG interpretation, PDF report generation, Bluetooth connectivity, 30-second recording time, Cloud storage for ECG history, Physician dashboard access'),

('HM-004', 'SleepWell Analytics Band',
 'An advanced sleep monitoring wearable that tracks sleep stages, breathing patterns, and sleep quality using clinical-grade sensors. Provides personalized insights and recommendations to improve sleep health.',
 'Sleep Monitoring',
 'Sleep stage detection (REM, Deep, Light), Blood oxygen monitoring during sleep, Snoring detection and analysis, Sleep apnea screening, Heart rate tracking overnight, Sleep quality scoring, Personalized sleep coaching, Smart alarm with optimal wake time, 7-day battery life, Skin temperature monitoring'),

('HM-005', 'PulseOx Fingertip Scanner',
 'A clinical-grade fingertip pulse oximeter that provides instant and accurate blood oxygen saturation and pulse rate readings. Designed for both professional healthcare settings and home monitoring.',
 'Vital Signs Monitoring',
 'SpO2 accuracy ±2%, Pulse rate monitoring, Perfusion index measurement, Plethysmograph waveform display, OLED dual-color display, Auto power-off, Low battery indicator, Suitable for ages 4+, Lanyard included, 30-hour battery life'),

('HM-006', 'NeuroFocus EEG Headband',
 'A consumer-friendly EEG headband that monitors brain wave activity to assess focus, stress, and cognitive performance. Used for meditation guidance, neurofeedback training, and cognitive health tracking.',
 'Neurological Monitoring',
 '4-channel EEG sensor array, Real-time brainwave analysis, Focus and attention scoring, Stress level monitoring, Guided meditation modes, Neurofeedback training programs, Bluetooth 5.0, Companion mobile app, 10-hour battery life, Lightweight ergonomic design');

-- ============================================================
-- Table Schema Summary
-- ============================================================
-- | Column       | Type         | Constraints          |
-- |-------------|-------------|----------------------|
-- | product_id   | VARCHAR(10)  | PRIMARY KEY, NOT NULL |
-- | product_name | VARCHAR(150) | NOT NULL              |
-- | description  | TEXT         |                       |
-- | category     | VARCHAR(100) |                       |
-- | key_features | TEXT         |                       |
-- ============================================================
