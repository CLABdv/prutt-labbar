public class Main {
    public static void main(String[] args) {
        Composite suitcase = new Composite(3000, "suitcase");
        Composite mediumBag = new Composite(1000,"medium bag");
        Composite smallBag = new Composite(500, "small bag");
        Leaf superImportantThing = new Leaf(50, "super important thing");
        suitcase.add(mediumBag);
        mediumBag.add(smallBag);
        smallBag.add(superImportantThing);

        Composite[] bags = {suitcase, mediumBag, smallBag};
        
        for (int i=0; i < 10; i++) { // expensive to bring weights on a flight
            bags[i%3].add(new Leaf((double) (i+1)*100, (i+1)+" hg weight"));
        }
        System.out.println("weight of suitcase: "+suitcase.getWeight());
        System.out.println("things:\n"+suitcase.toString());
        suitcase.remove(mediumBag);
        System.out.println();
        System.out.println("weight of suitcase: "+suitcase.getWeight());
        System.out.println("things:\n"+suitcase.toString());
    }
}
