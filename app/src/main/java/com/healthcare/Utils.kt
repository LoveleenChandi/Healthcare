package com.healthcare

import android.content.Context
import com.healthcare.models.Doctor
import com.healthcare.models.LabTestPackage
import com.healthcare.models.Medicine

class Utils {
    val labTestPackages = listOf(
        LabTestPackage(
            name = "Basic Blood Test",
            tests = listOf("Complete Blood Count", "Blood Glucose Test"),
            price = "$30",
            instructions = "Fasting required for 8 hours before the test."
        ),
        LabTestPackage(
            name = "Urine Analysis",
            tests = listOf("Urinalysis"),
            price = "$20",
            instructions = "No special instructions."
        ),
        LabTestPackage(
            name = "Thyroid Function Test",
            tests = listOf("TSH Test", "Free T4 Test"),
            price = "$40",
            instructions = "Fasting required for 8 hours before the test."
        ),
        LabTestPackage(
            name = "Liver Function Test",
            tests = listOf("ALT Test", "AST Test", "ALP Test"),
            price = "$35",
            instructions = "No special instructions."
        ),
        LabTestPackage(
            name = "Renal Function Test",
            tests = listOf("Creatinine Test", "BUN Test"),
            price = "$25",
            instructions = "No special instructions."
        ),
        LabTestPackage(
            name = "Cholesterol Panel",
            tests = listOf("Total Cholesterol Test", "HDL Test", "LDL Test"),
            price = "$45",
            instructions = "Fasting required for 8 hours before the test."
        ),
        LabTestPackage(
            name = "Cancer Screening",
            tests = listOf("PSA Test", "PAP Smear"),
            price = "$50",
            instructions = "No special instructions."
        ),
        LabTestPackage(
            name = "Allergy Testing",
            tests = listOf("Skin Prick Test", "Blood Allergy Test"),
            price = "$60",
            instructions = "No special instructions."
        ),
        LabTestPackage(
            name = "Coagulation Panel",
            tests = listOf("Prothrombin Time Test", "Partial Thromboplastin Time Test"),
            price = "$55",
            instructions = "No special instructions."
        ),
        LabTestPackage(
            name = "Vitamin D Test",
            tests = listOf("Vitamin D Test"),
            price = "$30",
            instructions = "No special instructions."
        ),
        LabTestPackage(
            name = "Hepatitis Panel",
            tests = listOf("Hepatitis A Test", "Hepatitis B Test", "Hepatitis C Test"),
            price = "$75",
            instructions = "No special instructions."
        ),
        LabTestPackage(
            name = "STD Panel",
            tests = listOf("HIV Test", "Syphilis Test", "Chlamydia Test", "Gonorrhea Test"),
            price = "$80",
            instructions = "No special instructions."
        ),
        LabTestPackage(
            name = "Metabolic Panel",
            tests = listOf("Electrolyte Panel", "Kidney Function Panel", "Liver Function Panel"),
            price = "$90",
            instructions = "Fasting required for 8 hours before the test."
        ),
        LabTestPackage(
            name = "Cardiac Enzyme Panel",
            tests = listOf("Troponin Test", "CK-MB Test", "LDH Test"),
            price = "$70",
            instructions = "No special instructions."
        ),
        LabTestPackage(
            name = "Pregnancy Test",
            tests = listOf("HCG Test"),
            price = "$15",
            instructions = "No special instructions."
        )
    )
    val medicines = listOf(
        Medicine("Paracetamol", "500mg", 5.99, "ABC Pharmaceuticals"),
        Medicine("Ibuprofen", "200mg", 7.49, "XYZ Pharmaceuticals"),
        Medicine("Amoxicillin", "250mg", 9.99, "DEF Pharmaceuticals"),
        Medicine("Aspirin", "81mg", 3.99, "GHI Pharmaceuticals"),
        Medicine("Omeprazole", "20mg", 12.99, "JKL Pharmaceuticals"),
        Medicine("Ciprofloxacin", "500mg", 11.99, "MNO Pharmaceuticals"),
        Medicine("Loratadine", "10mg", 8.99, "PQR Pharmaceuticals"),
        Medicine("Diazepam", "5mg", 15.49, "STU Pharmaceuticals"),
        Medicine("Cetirizine", "10mg", 6.99, "VWX Pharmaceuticals"),
        Medicine("Atorvastatin", "20mg", 18.99, "YZA Pharmaceuticals"),
        Medicine("Metformin", "500mg", 7.99, "BCD Pharmaceuticals"),
        Medicine("Prednisone", "5mg", 9.99, "EFG Pharmaceuticals"),
        Medicine("Simvastatin", "20mg", 14.99, "HIJ Pharmaceuticals"),
        Medicine("Tramadol", "50mg", 10.99, "KLM Pharmaceuticals"),
        Medicine("Lisinopril", "10mg", 6.49, "NOP Pharmaceuticals")
    )
    val doctors = listOf(
        Doctor(
            name = "Dr. John Smith",
            specialty = "Cardiologist",
            qualifications = "MD, Cardiology",
            availability = "Mon-Fri, 9am-5pm",
            reviews = listOf(
                "Great doctor, highly recommended!",
                "Very knowledgeable and friendly."
            )
        ),
        Doctor(
            name = "Dr. Sarah Johnson",
            specialty = "Pediatrician",
            qualifications = "MD, Pediatrics",
            availability = "Mon-Sat, 8am-6pm",
            reviews = listOf(
                "Excellent with kids, my children love her!",
                "Always listens to concerns and provides thorough explanations."
            )
        ),
        Doctor(
            name = "Dr. Michael Lee",
            specialty = "Dermatologist",
            qualifications = "MD, Dermatology",
            availability = "Tue-Thu, 10am-4pm",
            reviews = listOf(
                "Very professional and skilled.",
                "Provides effective treatment options."
            )
        ),
        Doctor(
            name = "Dr. Emily Brown",
            specialty = "Obstetrician/Gynecologist",
            qualifications = "MD, Obstetrics and Gynecology",
            availability = "Mon-Fri, 9am-5pm",
            reviews = listOf(
                "Empathetic and caring.",
                "Highly recommended for women's health needs."
            )
        ),
        Doctor(
            name = "Dr. David Wilson",
            specialty = "Orthopedic Surgeon",
            qualifications = "MD, Orthopedic Surgery",
            availability = "Mon-Wed, Fri, 9am-3pm",
            reviews = listOf(
                "Skilled surgeon, helped me recover quickly.",
                "Explains treatment options clearly."
            )
        ),
        Doctor(
            name = "Dr. Jennifer Garcia",
            specialty = "Psychiatrist",
            qualifications = "MD, Psychiatry",
            availability = "Mon-Fri, 10am-6pm",
            reviews = listOf(
                "Listens attentively and provides valuable insights.",
                "Helped me manage my mental health effectively."
            )
        ),
        Doctor(
            name = "Dr. Robert Turner",
            specialty = "Ophthalmologist",
            qualifications = "MD, Ophthalmology",
            availability = "Tue, Thu, Fri, 10am-5pm",
            reviews = listOf(
                "Skilled eye doctor, improved my vision.",
                "Friendly staff and efficient service."
            )
        ),
        Doctor(
            name = "Dr. Maria Rodriguez",
            specialty = "Endocrinologist",
            qualifications = "MD, Endocrinology",
            availability = "Mon, Wed, Fri, 8am-4pm",
            reviews = listOf(
                "Very knowledgeable about hormone disorders.",
                "Helped me manage my diabetes effectively."
            )
        ),
        Doctor(
            name = "Dr. William Clark",
            specialty = "Neurologist",
            qualifications = "MD, Neurology",
            availability = "Tue, Thu, Sat, 9am-5pm",
            reviews = listOf(
                "Highly skilled doctor, accurately diagnosed my condition.",
                "Excellent bedside manner."
            )
        ),
        Doctor(
            name = "Dr. Jessica Martinez",
            specialty = "General Practitioner",
            qualifications = "MD, General Medicine",
            availability = "Mon-Sat, 9am-7pm",
            reviews = listOf(
                "Caring and compassionate doctor.",
                "Provides comprehensive care for the whole family."
            )
        ),
        Doctor(
            name = "Dr. Daniel White",
            specialty = "Urologist",
            qualifications = "MD, Urology",
            availability = "Mon, Wed, Fri, 10am-4pm",
            reviews = listOf(
                "Very professional and thorough in examinations.",
                "Effective treatment options for urological issues."
            )
        ),
        Doctor(
            name = "Dr. Laura Thompson",
            specialty = "Allergist/Immunologist",
            qualifications = "MD, Allergy and Immunology",
            availability = "Tue, Thu, Sat, 8am-6pm",
            reviews = listOf(
                "Helped me identify and manage my allergies effectively.",
                "Great communication and patient care."
            )
        ),
        Doctor(
            name = "Dr. Richard Baker",
            specialty = "Gastroenterologist",
            qualifications = "MD, Gastroenterology",
            availability = "Mon-Fri, 8am-4pm",
            reviews = listOf(
                "Highly skilled in diagnosing and treating gastrointestinal issues.",
                "Provides personalized treatment plans."
            )
        ),
        Doctor(
            name = "Dr. Samantha King",
            specialty = "Rheumatologist",
            qualifications = "MD, Rheumatology",
            availability = "Mon, Wed, Fri, 9am-3pm",
            reviews = listOf(
                "Very knowledgeable about autoimmune conditions.",
                "Empathetic and caring towards patients."
            )
        ),
        Doctor(
            name = "Dr. Christopher Evans",
            specialty = "Pulmonologist",
            qualifications = "MD, Pulmonology",
            availability = "Tue, Thu, Sat, 10am-5pm",
            reviews = listOf(
                "Helped me manage my respiratory issues effectively.",
                "Thorough in examinations and treatment."
            )
        )
    )

}