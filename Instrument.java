@Test
    public void testOud() throws Exception {
        //final List<String> notes = new ArrayList<>();
   /*System.out.println(scaleBuilder("A",ScaleType.MAJOR.getPattern()));
   System.out.println(scaleBuilder("B",ScaleType.MAJOR.getPattern()));
   System.out.println(scaleBuilder("C",ScaleType.MAJOR.getPattern()));
   System.out.println(scaleBuilder("D",ScaleType.MAJOR.getPattern()));
   System.out.println(scaleBuilder("E",ScaleType.MAJOR.getPattern()));
   System.out.println(scaleBuilder("F",ScaleType.MAJOR.getPattern()));
   System.out.println(scaleBuilder("G",ScaleType.MAJOR.getPattern()));
   System.out.println(scaleBuilder("A",ScaleType.MINOR.getPattern()));
   System.out.println(scaleBuilder("B",ScaleType.MINOR.getPattern()));
   System.out.println(scaleBuilder("C",ScaleType.MINOR.getPattern()));
   System.out.println(scaleBuilder("D",ScaleType.MINOR.getPattern()));
   System.out.println(scaleBuilder("E",ScaleType.MINOR.getPattern()));
   System.out.println(scaleBuilder("F",ScaleType.MINOR.getPattern()));
   System.out.println(scaleBuilder("G",ScaleType.MINOR.getPattern()));
   System.out.println(scaleBuilder("D",ScaleType.BAYATI.getPattern()));
   System.out.println(scaleBuilder("A",ScaleType.BAYATI.getPattern()));
   System.out.println(scaleBuilder("D",ScaleType.HIJAZ.getPattern()));
   System.out.println(scaleBuilder("A",ScaleType.HIJAZ.getPattern()));
   System.out.println(scaleBuilder("C",ScaleType.CHROMATIC.getPattern()));
   System.out.println(scaleBuilder("e",ScaleType.PENTATONIC.getPattern()));*/

        IInstrument instrument = new Guitar("Yamaha",2006,"E","B","G","D","A","E");
        System.out.println(scaleBuilder("e",ScaleType.PENTATONIC.getPattern()));

    }

    //Build scale using scaleType and starting tonic
    public List<String> scaleBuilder(final String note, final String pattern){
        final List<String> notes = adjustScale(note);
        List<String> newScale = new ArrayList<>();

        int posCnt = 0;
        newScale.add(notes.get(posCnt));
        String[] patternArray = pattern.split("");
        for (String aPattern : patternArray) {
            if(posCnt < 12) {
                if (aPattern.equals("W")) {
                    posCnt += ScaleType.getWholeStep();
                    newScale.add(notes.get(posCnt));
                } else if (aPattern.equals("H")) {
                    posCnt += ScaleType.getHalfStep();
                    newScale.add(notes.get(posCnt));
                } else if (aPattern.equals("h")) {
                    posCnt += ScaleType.getHalfStep();
                    newScale.add(notes.get(posCnt)+"h");
                } else if (aPattern.equals("t")) {
                    posCnt += ScaleType.getMinorThirdStep();
                    newScale.add(notes.get(posCnt));
                } else if (aPattern.equals("T")) {
                    posCnt += ScaleType.getMajorThirdStep();
                    newScale.add(notes.get(posCnt));
                }
            } else {
                break;
            }
        }
        return newScale;
    }

    //Adjust chromatic to start with tonic
    public List<String> adjustScale(final String tonic){
        final LinkedList<String> notes = noteBuilder();
        List<String> newNotes = new ArrayList<>();
        List<String> temp = new ArrayList<>();

        int index = notes.indexOf(tonic);
        for(int i = 0; i < index; i++){
            temp.add(notes.removeFirst());
        }
        while(! notes.isEmpty()){
            newNotes.add(notes.removeFirst());
        }
        newNotes.addAll(temp);
        newNotes.addAll(newNotes);
        newNotes.add(tonic);

        return newNotes;
    }

    public enum ScaleType {
        //Scale Types with formula and available starting tonics
        //ALL symbolising all starting whole notes
        //W:Whole step,H:Half step,h:Half step/ half flat, t=Minor Third step
        //Some scales evaluate to full 8 note chords, 12 note or tetrachord
        MAJOR("WWHWWWH","ALL"),
        MINOR("WHWWHWW","ALL"),
        BAYATI("hWW","A","D"),
        HIJAZ("HtH","A","D"),
        WHOLE_TONE("WWWWWWW","ALL"),
        PENTATONIC("WWtWt","ALL"),
        CHROMATIC("HHHHHHHHHHHH","ALL");

        private final static int HALF_NOTE_INTERVAL = 1;
        private final static int WHOLE_NOTE_INTERVAL = 2;
        private final static int MINOR_THIRD_INTERVAL = 3;
        private final static int MAJOR_THIRD_INTERVAL = 4;

        private String pattern;
        private String[] tonicValues;

        ScaleType(final String pattern,final String... tonicValues) {
            this.pattern = pattern;
            this.tonicValues = tonicValues;
        }

        public String getPattern(){
            return pattern;
        }

        public String[] getTonicValues() {
            return tonicValues;
        }

        public static int getWholeStep(){
            return WHOLE_NOTE_INTERVAL;
        }

        public static int getHalfStep(){
            return HALF_NOTE_INTERVAL;
        }

        public static int getMinorThirdStep(){
            return MINOR_THIRD_INTERVAL;
        }

        public static int getMajorThirdStep(){
            return MAJOR_THIRD_INTERVAL;
        }

        public static ScaleType fromPatternString(final String pattern) {
            for (ScaleType scaleType : ScaleType.values()) {
                if (scaleType.getPattern().equals(pattern)) {
                    return scaleType;
                }
            }
            return null;
        }
    }

    //Build default chromatic scale staring wih default A
    public LinkedList<String> noteBuilder(){
        //Uppercase for whole notes, lower for flats
        //Using FIFO queue to easily alter
        LinkedList<String> notes = new LinkedList<>();
        notes.add("A");
        notes.add("b");
        notes.add("B");
        notes.add("C");
        notes.add("d");
        notes.add("D");
        notes.add("e");
        notes.add("E");
        notes.add("F");
        notes.add("g");
        notes.add("G");
        notes.add("a");

        return notes;
    }

    public interface IInstrument {
        String getName();
        String getMake();
        int getYear();
        String[] getTuning();
        void setMake(String make);
        void setYear(int year);
        void setTuning(String[] tuning);
    }

    public class Guitar implements IInstrument {
        private String name;
        private String make;
        private String[] tuning;
        private int year;

        public Guitar(final String make,final int year,final String... tuning){
            this.name = "GUITAR";
            this.make = make;
            this.year = year;
            this.tuning = tuning;
        }

        public Guitar() {
            this.name = "GUITAR";
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getMake() {
            return make;
        }

        @Override
        public int getYear() {
            return year;
        }

        @Override
        public String[] getTuning() {
            return tuning;
        }

        @Override
        public void setMake(String make) {
            this.make = make;
        }

        @Override
        public void setYear(int year) {
            this.year = year;
        }

        @Override
        public void setTuning(String[] tuning) {
            this.tuning = tuning;
        }
    }

    public class Oud implements IInstrument {
        private String name;
        private String make;
        private String[] tuning;
        private int year;

        public Oud(final String make,final int year,final String... tuning) {
            this.name = "OUD";
            this.make = make;
            this.year = year;
            this.tuning = tuning;
        }

        public Oud() {
            this.name = "OUD";
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getMake() {
            return make;
        }

        @Override
        public int getYear() {
            return year;
        }

        @Override
        public String[] getTuning() {
            return tuning;
        }

        @Override
        public void setMake(String make) {
            this.make = make;
        }

        @Override
        public void setYear(int year) {
            this.year = year;
        }

        @Override
        public void setTuning(String[] tuning) {
            this.tuning = tuning;
        }
    }
