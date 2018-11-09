private void fetchLyrics(String artist, String song, final LyricFetcherCallback lyricFetcherCallback) {
    String url = "http://lyrics.wikia.com/wiki/" + artist + ":" + song;

    HTMLDataFetcher lyricsHTMLFetch = new HTMLDataFetcher(mContext, url, new HTMLDataFetcher.HTMLDataFetcherCallback() {
      @Override
      public void onSuccess(Document result) {
        String lyrics = result.select(".lyricbox").html();

        // remove everything between brackets
        lyrics = lyrics.replaceAll("\\[[^\\]]*\\]", "");
        // remove html comments
        lyrics = lyrics.replaceAll("(<!--)[^-]*-->", "");
        // replace newlines
        lyrics = lyrics.replaceAll("<br>", "\n");
        // remove all tags
        lyrics = lyrics.replaceAll("<[^>]*>", "");

        if(!lyrics.equals("")){
          lyricFetcherCallback.onSuccess(lyrics);
        }
        else{
          lyricFetcherCallback.onError("Not found");
        }

      }

      @Override
      public void onError() {
        lyricFetcherCallback.onError("");
      }
    });
    lyricsHTMLFetch.execute();
  }

  interface LyricFetcherCallback {
    void onSuccess(String lyrics);
    void onError(String error);
  }
