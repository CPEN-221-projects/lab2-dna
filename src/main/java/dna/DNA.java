package dna;

// TODO: Implement the DNA datatype from scratch!
// Use the test cases to guide you.

import java.util.ArrayList;
import java.util.HashSet;

public class DNA {
    private String nucleotideSequence;
    public DNA(String nucleotideSequence) {
        this.nucleotideSequence = nucleotideSequence;

        if (!validDNA(nucleotideSequence)) {
            throw new IllegalArgumentException("Invalid DNA sequence");
        }
    }
    private boolean validDNA(String nucleotideSequence) {
        int nucleotideCount = 0;
        for (int i = 0; i < nucleotideSequence.length(); i++) {
            if (!isJunk(nucleotideSequence.charAt(i))) {
                nucleotideCount++;
            }
        }
        return nucleotideCount % 3 == 0;
    }
    public ArrayList<String> codonList() {
        ArrayList<String> codonsInOrder = new ArrayList<>();

        String currentCodon = "";

        for(int i = 0; i < nucleotideSequence.length(); i++) {
            if (!isJunk(nucleotideSequence.charAt(i))) {

                currentCodon += nucleotideSequence.charAt(i);

                if (currentCodon.length() == 3) {
                    codonsInOrder.add(currentCodon);
                    currentCodon = "";
                }

            }
        }
        return codonsInOrder;
    }
    public boolean isProtein() {

        ArrayList<String> codonsInOrder = codonList();

        if (isStartCodon(codonsInOrder.get(0)) &&
                isEndCodon(codonsInOrder.get(codonsInOrder.size()-1)) &&
                codonsInOrder.size() - 1 >= 5 &&
                cytosineGuanineMassRatio()) {
            return true;
        }

        return false;
    }
    private boolean isStartCodon(String codon) {
        return codon.equals("ATG");
    }
    private boolean isEndCodon(String codon) {
        return codon.equals("TAA") || codon.equals("TAG") || codon.equals("TGA");
    }
    private boolean cytosineGuanineMassRatio() {
        double cgMass = 0;
        for (int i = 0; i < nucleotideSequence.length(); i++) {

            char currentNucleotide = nucleotideSequence.charAt(i);

            switch(currentNucleotide) {
                case 'C':
                    cgMass += 111.103;
                    break;
                case 'G':
                    cgMass += 151.128;
            }

        }

        return cgMass / totalMass() >= 0.3;
    }
    public double totalMass() {
        double dnaMass = 0;

        for (int i = 0; i < nucleotideSequence.length(); i++) {

            char currentNucleotide = nucleotideSequence.charAt(i);

            switch(currentNucleotide) {
                case 'A':
                    dnaMass += 135.128;
                    break;
                case 'C':
                    dnaMass += 111.103;
                    break;
                case 'T':
                    dnaMass += 125.107;
                    break;
                case 'G':
                    dnaMass += 151.128;
                    break;
                default:
                    dnaMass += 100.000;
            }

        }

        return Math.round(dnaMass * 10.0) / 10.0;
    }
    public int nucleotideCount(char nucleotide) {

        int count = 0;

        if (isJunk(nucleotide)) {
            return 0;
        }

        for (int i = 0; i < nucleotideSequence.length(); i++) {
            if (nucleotideSequence.charAt(i) == nucleotide) {
                count++;
            }
        }

        return count;
    }
    public HashSet<String> codonSet() {
        HashSet<String> codonSet = new HashSet<>();
        ArrayList<String> codonsInOrder = codonList();

        for (String codon: codonsInOrder) {
            codonSet.add(codon);
        }
        return codonSet;
    }
    public void mutateCodon(String originalCodon, String newCodon) {
        ArrayList<String> codonsInOrder = codonList();
        ArrayList<String> mutateList = new ArrayList<>();

        if (!validDNA(originalCodon) || !validDNA(newCodon)) {
            return;
        }

        for (String codon: codonsInOrder) {
            if (codon.equals(originalCodon)) {
                mutateList.add(newCodon);
            } else {
                mutateList.add(codon);
            }
        }

        nucleotideSequence = "";

        for (String codon: mutateList) {
            for (int i = 0; i < codon.length(); i++) {
                nucleotideSequence += codon.charAt(i);
            }
        }
    }
    private boolean isJunk(char c) {
        return !(c == 'A' || c == 'C' || c == 'T' || c == 'G');
    }
    public String sequence() {
        return this.nucleotideSequence;
    }
}