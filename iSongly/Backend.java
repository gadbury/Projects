import java.io.IOException;
import java.util.*;
import java.io.File;
import java.util.Comparator;

public class Backend implements BackendInterface{
    private IterableSortedCollection<Song> tree = null;
    private int titleIndex;
    private int artistIndex;
    private int topGenreIndex;
    private int yearIndex;
    private int bpmIndex;
    private int nrgyIndex;
    private int dnceIndex;
    private int dBIndex;
    private int liveIndex;
    private Integer loudnessThreshold = 999;
    private Integer yearHigh = 9999;
    private Integer yearLow = 0;



    public Backend(IterableSortedCollection<Song> tree){
        this.tree = tree;
    }

    /**
     * Loads data from the .csv file referenced by filename.  You can rely
     * on the exact headers found in the provided songs.csv, but you should
     * not rely on them always being presented in this order or on there
     * not being additional columns describing other song qualities.
     * After reading songs from the file, the songs are inserted into
     * the tree passed to the constructor.
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     *
     */
    @Override
    public void readData(String filename)throws IOException{
        Scanner input = new Scanner(new File(filename));

        //First read header
        String fileHeader = input.nextLine();
        String[] infoTypes = fileHeader.split(",");

        //Create variables for the indexes of each info type's index
        for(int i = 0; i < infoTypes.length; i++){
            switch(infoTypes[i].trim()){
                case "title" -> {titleIndex = i;}
                case "artist" -> {artistIndex = i;}
                case "top genre" -> {topGenreIndex = i;}
                case "year" -> {yearIndex = i;}
                case "bpm" -> {bpmIndex = i;}
                case "nrgy" -> {nrgyIndex = i;}
                case "dnce" -> {dnceIndex = i;}
                case "dB" -> {dBIndex = i;}
                case "live" -> {liveIndex = i;}
            }
        }


        //Insert all songs into tree
        while(input.hasNextLine()){
            Song thisSong = readDataHelper(input.nextLine());
            tree.insert(thisSong);
        }
    }

    //Helper method to take in lines from csv file and convert them to song objects
    private Song readDataHelper(String songData){
        boolean inQuotes = false;
        ArrayList<String> songArguments = new ArrayList<>();
        StringBuilder currentArgument = new StringBuilder();

        String title;
        String artist;
        String genre;
        int year;
        int bpm;
        int energy;
        int danceability;
        int loudness;
        int liveness;

        for(int i = 0; i < songData.length(); i++){
            char thisChar = songData.charAt(i);
            char nextChar = ' ';
            if(i != songData.length()-1){
                nextChar = songData.charAt(i+1);
            }

            if(thisChar == '"'){
                //If double quotes are encountered, add one quote to currentArgument and move
                //for loop and extra char ahead so second quote is not read
                if(nextChar == '"'){
                    currentArgument.append('"');
                    i++;
                }
                //If char is a solitary quote, toggle inQuotes and don't add quote
                else{
                    inQuotes = !inQuotes;
                }
            }
            //If char is a comma outside of quotes, add currentArgument to songArguments and clear
            //currentArgument
            else if(thisChar == ','){
                if(!inQuotes){
                    songArguments.add(currentArgument.toString().trim());
                    currentArgument.setLength(0);
                }
                else{
                    currentArgument.append(thisChar);
                }
            }
            //If not a special character, add character to currentArgument
            else{
                currentArgument.append(thisChar);
            }
        }
        //Add last argument, as there is no final comma
        songArguments.add(currentArgument.toString().trim());

        //Create variables for each possible argument in the song constructor
        title = songArguments.get(titleIndex);
        artist = songArguments.get(artistIndex);
        genre = songArguments.get(topGenreIndex);
        year = Integer.parseInt(songArguments.get(yearIndex));
        bpm = Integer.parseInt(songArguments.get(bpmIndex));
        energy = Integer.parseInt(songArguments.get(nrgyIndex));
        danceability = Integer.parseInt(songArguments.get(dnceIndex));
        loudness = Integer.parseInt(songArguments.get(dBIndex));
        liveness = Integer.parseInt(songArguments.get(liveIndex));

        return new Song(title,artist,genre,year,bpm,energy,danceability,loudness,liveness);
    }

    /**
     * Retrieves a list of song titles from the tree passed to the contructor.
     * The songs should be ordered by the songs' Year, and that fall within
     * the specified range of Year values.  This Year range will
     * also be used by future calls to the setFilter and getmost Danceable methods.
     *
     * If a Loudness filter has been set using the setFilter method
     * below, then only songs that pass that filter should be included in the
     * list of titles returned by this method.
     *
     * When null is passed as either the low or high argument to this method,
     * that end of the range is understood to be unbounded.  For example, a null
     * high argument means that there is no maximum Year to include in
     * the returned list.
     *
     * @param low is the minimum Year of songs in the returned list
     * @param high is the maximum Year of songs in the returned list
     * @return List of titles for all songs from low to high, or an empty
     *     list when no such songs have been loaded
     */
    @Override
    public List<String> getRange(Integer low, Integer high) {
        ArrayList<String> titlesInRange = new ArrayList<>();
        ArrayList<Song> songsInRange = new ArrayList<>();
        //Sets yearLow field to equal argument passed, unless argument is null.
        if(low != null){
            yearLow = low;
        }
        else{
            yearLow = 0;
        }
        if(high != null){
            yearHigh = high;
        }
        else{
            yearHigh = 9999;
        }
        //get iterator from tree
        Iterator<Song> rangeIterator = tree.iterator();

        //Iterate through iterator and add valid songs
        while (rangeIterator.hasNext()) {
            Song thisSong = rangeIterator.next();
            //Only add songs below threshold and in range
            if ((thisSong.getLoudness() < loudnessThreshold) &&
                (thisSong.getYear() <= yearHigh && thisSong.getYear() >= yearLow)) {
                songsInRange.add(thisSong);
            }
        }
        //Put ArrayList of songs in year order using comparator
        songsInRange.sort(new myComparator("year"));

        //Create final ArrayList of song titles
        for(int i = 0; i < songsInRange.size(); i++){
            titlesInRange.add(songsInRange.get(i).getTitle());
            }

        return titlesInRange;
        }

    /**
     * Retrieves a list of song titles that have a Loudness that is
     * smaller than the specified threshold.  Similar to the getRange
     * method: this list of song titles should be ordered by the songs'
     * Year, and should only include songs that fall within the specified
     * range of Year values that was established by the most recent call
     * to getRange.  If getRange has not previously been called, then no low
     * or high Year bound should be used.  The filter set by this method
     * will be used by future calls to the getRange and getmost Danceable methods.
     *
     * When null is passed as the threshold to this method, then no Loudness
     * threshold should be used.  This effectively clears the filter.
     *
     * @param threshold filters returned song titles to only include songs that
     *     have a Loudness that is smaller than this threshold.
     * @return List of titles for songs that meet this filter requirement, or
     *     an empty list when no such songs have been loaded
     */
    @Override
    public List<String> setFilter(Integer threshold) {
        if(threshold != null){
            loudnessThreshold = threshold;
        }
        else{
            loudnessThreshold = 240;
        }
        return getRange(this.yearLow,this.yearHigh);
    }

    /**
     * This method returns a list of song titles representing the five
     * most Danceable songs that both fall within any attribute range specified
     * by the most recent call to getRange, and conform to any filter set by
     * the most recent call to setFilter.  The order of the song titles in this
     * returned list is up to you.
     *
     * If fewer than five such songs exist, return all of them.  And return an
     * empty list when there are no such songs.
     *
     * @return List of five most Danceable song titles
     */
    @Override
    public List<String> fiveMost() {
        List<String> danceableSongs = new ArrayList<>();

        Iterator<Song> danceIterator = tree.iterator();

        //Convert iterator to list with songs fitting other criteria
        List<Song> danceList = new ArrayList<>();
        while(danceIterator.hasNext()){
            Song thisSong = danceIterator.next();
            //Only add song if it fits other criteria
            if(thisSong.getYear() >= this.yearLow &&
                thisSong.getYear() <= this.yearHigh &&
                  thisSong.getLoudness() < this.loudnessThreshold){
                danceList.add(thisSong);
            }
        }



        //If there's no songs in range, return empty list
        if(danceList.size() == 0){
            return danceableSongs;
        }

        // Sort the list by danceability in descending order
        danceList.sort((s1, s2) -> Double.compare(s2.getDanceability(), s1.getDanceability()));

        //If there's <5 songs in range, return all titles in range
        if(danceList.size() < 5) {
            for (int i = 0; i < danceList.size(); i++) {
                danceableSongs.add(danceList.get(i).getTitle());
            }
            return danceableSongs;
        }

    	// Add the titles of the top 5 danceable songs
    	for (int i = 0; i < 5; i++) {
        	danceableSongs.add(danceList.get(i).getTitle());
    	}

        return danceableSongs;
    }
}

