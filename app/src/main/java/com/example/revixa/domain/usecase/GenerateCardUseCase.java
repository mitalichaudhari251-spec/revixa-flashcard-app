package com.example.revixa.domain.usecase;

import com.example.revixa.domain.model.Card;
import com.example.revixa.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class GenerateCardUseCase {

    private static final Map<String, String[][]> TEMPLATES = new HashMap<>();

    static {
        TEMPLATES.put("Physics", new String[][] {
            {"Newton's First Law",  "What is Newton's First Law of Motion?",  "An object at rest stays at rest, and an object in motion continues in motion with constant velocity unless acted upon by a net external force."},
            {"Newton's Second Law", "State Newton's Second Law of Motion.",   "F = ma — Net force equals mass times acceleration."},
            {"Ohm's Law",           "State Ohm's Law.",                        "V = IR — Voltage equals current multiplied by resistance."},
            {"Kinetic Energy",      "Write the formula for kinetic energy.",   "KE = ½mv² — where m is mass (kg) and v is velocity (m/s)."},
            {"Gravitational PE",    "Write the formula for gravitational PE.", "PE = mgh — mass × gravitational acceleration × height."},
        });
        TEMPLATES.put("Mathematics", new String[][] {
            {"Quadratic Formula",   "What is the quadratic formula?",          "x = (−b ± √(b²−4ac)) / 2a, for ax² + bx + c = 0"},
            {"Pythagorean Theorem", "State the Pythagorean Theorem.",          "a² + b² = c², where c is the hypotenuse of a right triangle."},
            {"Area of Circle",      "What is the area of a circle?",           "A = πr², where r is the radius."},
            {"Derivative Power Rule","State the power rule for derivatives.",  "d/dx[xⁿ] = nxⁿ⁻¹"},
            {"Sum of Arithmetic",   "Formula for sum of arithmetic series?",   "S = n/2 × (first term + last term)"},
        });
        TEMPLATES.put("Chemistry", new String[][] {
            {"Avogadro's Number",   "What is Avogadro's number?",              "6.022 × 10²³ — number of particles in one mole of a substance."},
            {"Boyle's Law",         "State Boyle's Law.",                       "At constant temperature: P₁V₁ = P₂V₂"},
            {"Charles's Law",       "State Charles's Law.",                     "At constant pressure: V₁/T₁ = V₂/T₂"},
            {"pH Definition",       "Define pH.",                               "pH = −log[H⁺]. Values below 7 are acidic; above 7 are basic."},
            {"Ideal Gas Law",       "State the ideal gas law.",                 "PV = nRT — pressure × volume = moles × gas constant × temperature."},
        });
        TEMPLATES.put("Biology", new String[][] {
            {"Photosynthesis",      "Write the equation for photosynthesis.",   "6CO₂ + 6H₂O + light → C₆H₁₂O₆ + 6O₂"},
            {"Cell Respiration",    "Write the equation for aerobic respiration.", "C₆H₁₂O₆ + 6O₂ → 6CO₂ + 6H₂O + ATP"},
            {"Mitosis Phases",      "List the phases of mitosis in order.",     "Prophase → Metaphase → Anaphase → Telophase → Cytokinesis"},
            {"DNA Bases",           "What are the base pairs in DNA?",          "Adenine–Thymine (A-T) and Guanine–Cytosine (G-C)"},
            {"Mendelian Law",       "State Mendel's Law of Segregation.",       "Each organism carries two alleles for each trait; they separate during gamete formation."},
        });
        TEMPLATES.put("Computer Sci", new String[][] {
            {"Binary Search",       "Time complexity of Binary Search?",        "O(log n) — halves the search space at every step."},
            {"Bubble Sort",         "Time complexity of Bubble Sort?",          "O(n²) worst/average case; O(n) best case (already sorted)."},
            {"Stack vs Queue",      "Difference between stack and queue?",      "Stack: LIFO (Last In First Out). Queue: FIFO (First In First Out)."},
            {"Big-O Notation",      "What does O(1) mean?",                     "Constant time — execution time does not depend on input size."},
            {"Recursion Base Case", "Why does recursion need a base case?",     "Without a base case the function calls itself infinitely, causing a stack overflow."},
        });
    }

    public Card generate(String categoryName) {
        String[][] templates = TEMPLATES.get(categoryName);
        if (templates == null) {
            String[][] generic = {
                {"Key Concept", "Define the key concept in this subject.", "Write a concise one-sentence definition here."},
            };
            templates = generic;
        }
        int idx = (int) (Math.random() * templates.length);
        String[] t = templates[idx];

        Card card = new Card();
        card.setTitle(t[0]);
        card.setQuestion(t[1]);
        card.setAnswer(t[2]);
        card.setCardType(Constants.TYPE_DEFINITION);
        card.setDifficulty(Constants.DIFFICULTY_MEDIUM);
        card.setPriority(Constants.PRIORITY_MEDIUM);
        return card;
    }
}
