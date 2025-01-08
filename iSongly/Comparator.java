import java.util.Comparator;

public class myComparator implements Comparator<Song> {

  private String criteria = "title";

  /**
   * Constructor for comparator
   *
   * @param criteria - The data of the song that will be used to compare songs
   */
  public myComparator(String criteria){
    switch (criteria.toLowerCase().trim()){
      case "year":
        this.criteria = "year";
        break;
      case "artist":
        this.criteria = "artist";
        break;
      case "top genre":
        this.criteria = "top genre";
        break;
      case "bpm":
        this.criteria = "bpm";
        break;
      case "nrgy":
        this.criteria = "nrgy";
        break;
      case "dnce":
        this.criteria = "dnce";
        break;
      case "dB":
        this.criteria = "dB";
        break;
      case "live":
        this.criteria = "live";
        break;

    }
  }
  @Override
  public int compare(Song o1, Song o2) {
    if (criteria.equals("artist")){
      return o1.getArtist().compareTo(o2.getArtist());
    }
    if (criteria.equals("top genre")){
      return o1.getGenres().compareTo(o2.getGenres());
    }
    if (criteria.equals("year")){
      return Integer.compare(o1.getYear(), o2.getYear());
    }
    if (criteria.equals("bpm")){
      return Integer.compare(o1.getBPM(), o2.getBPM());
    }
    if (criteria.equals("nrgy")){
      return Integer.compare(o1.getEnergy(), o2.getEnergy());
    }
    if (criteria.equals("dnce")){
      return Integer.compare(o1.getDanceability(), o2.getDanceability());
    }
    if (criteria.equals("dB")){
      return Integer.compare(o1.getLoudness(), o2.getLoudness());
    }
    if (criteria.equals("live")){
      return Integer.compare(o1.getLiveness(), o2.getLiveness());
    }
    else{
      return o1.getTitle().compareTo(o2.getTitle());
    }
  }
}
