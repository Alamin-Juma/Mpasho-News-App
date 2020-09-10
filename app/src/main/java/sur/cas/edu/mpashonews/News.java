package sur.cas.edu.mpashonews;

public class News {
    //web title  of the news
    String webTitle;

    //section name  of the news
    String sectionName;

    //pillar name  of the news
    String pillarName;

    //date  of the news
    String webPublicationDate;

    //web url  of the news
    String webUrl;

    // Author of the news  */
    String mNewsAuthor;


    /**
     * Constructs a new {@link News} object.
     *
     * @param webTitle           is the webTitle of the news
     * @param sectionName        of the news
     * @param webPublicationDate of news
     * @param mNewsAuthor        of news
     * @param webUrl             is the website URL to find more details about the news
     */
    public News(String webTitle, String sectionName, String mNewsAuthor,
                String webPublicationDate,
                String webUrl) {
        this.webTitle = webTitle;
        this.sectionName = sectionName;
        this.mNewsAuthor = mNewsAuthor;
        this.webPublicationDate = webPublicationDate;
        this.webUrl = webUrl;
    }


    public String getWebTitle() {
        return webTitle;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getPillarName() {
        return pillarName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getmNewsAuthor() {
        return mNewsAuthor;
    }
}
