//для хранения вероятностей в дробях
class Probabilities {
    private int numberInArray;
    private int numerator;
    private int denominator;

    Probabilities(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    Probabilities() {
    }

    Probabilities substring(Probabilities probabilities){
        numerator*=probabilities.denominator;
        probabilities.numerator*=denominator;
        denominator*=probabilities.denominator;
        probabilities.denominator=denominator;
        numerator=numerator-probabilities.numerator;
        return new Probabilities(numerator,denominator);

    }

    Probabilities multiply(Probabilities probabilities){
        numerator=numerator*probabilities.numerator;
        denominator=denominator*probabilities.denominator;
        return new Probabilities(numerator,denominator);
    }

    int compare(Probabilities probabilities2) {
        if (this.getDenominator() == 0 || probabilities2.getDenominator() == 0)
            return 0;
        int chisl1 = this.getNumerator() * probabilities2.getDenominator();
        int chisl2 = probabilities2.getNumerator() * this.getDenominator();
        return Integer.compare(chisl1, chisl2);
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    void set(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    int getDenominator() {
        return denominator;
    }

    int getNumerator() {
        return numerator;
    }

}
