package human;

public abstract class Human {
    String pnr;
    Human(){} // package-private by default
    public static Human create(String pnr) {
        if (pnr.charAt(9)=='0') {
            return new NonBinary(pnr);
        }
        else if (pnr.charAt(9)%2 == 0) {
            return new Man(pnr);
        }
        else {
            return new Woman(pnr);
        }
    }
}
