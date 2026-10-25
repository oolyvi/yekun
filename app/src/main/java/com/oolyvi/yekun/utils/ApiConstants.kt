package com.oolyvi.yekun.utils

object ApiConstants {

    /**
     * page size and offset values
     */
    var OFFSET_PAGE = 0
    const val COMMON_PAGE_SIZE = 25
    const val SEARCH_PAGE_SIZE = 50
    const val USER_LIST_PAGE_SIZE = 1000
    const val SEASON_PAGE_SIZE = 50


    /**
     * common and user fields
     */
    const val SEARCH_FIELDS =
        "id,title,main_picture,mean,media_type"
    const val RANKED_FIELDS = "alternative_titles{en,ja},mean"
    const val USER_FIELDS =
        "id,name,picture,gender,birthday,location,joined_at,anime_statistics,time_zone,is_supporter"


    /**
     * anime fields
     */
    private const val ANIME_LIST_STATUS_FIELDS =
        "start_date,finish_date,num_times_rewatched,is_rewatching,rewatch_value,priority,tags,comments,score"
    private const val ANIME_LIST_STATUS_DETAIL_FIELDS =
        "status,score,num_episodes_watched,is_rewatching,start_date,finish_date,priority,num_times_rewatched,rewatch_value,tags,comments,updated_at"
    const val USER_ANIME_LIST_FIELDS =
        "alternative_titles{en,ja},list_status{$ANIME_LIST_STATUS_FIELDS},num_episodes,media_type,status,broadcast"
    const val ANIME_DETAIL_FIELDS =
        "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank," +
                "popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at," +
                "media_type,status,genres,my_list_status{$ANIME_LIST_STATUS_DETAIL_FIELDS},num_episodes,start_season,broadcast," +
                "source,average_episode_duration,rating,pictures,background,related_anime," +
                "related_manga,recommendations,studios,statistics,opening_themes,ending_themes"
    const val SEASON_FIELDS =
        "alternative_titles{en,ja},start_season,broadcast,num_episodes,media_type,mean,num_list_users"


    /**
     * manga fields
     */
    private const val MANGA_LIST_STATUS_FIELDS =
        "start_date,finish_date,num_times_reread,is_rereading,reread_value,priority,tags,comments"
    private const val MANGA_LIST_STATUS_DETAIL_FIELDS =
        "start_date,finish_date,num_times_reread,is_rereading,reread_value,priority,tags,comments"
    const val USER_MANGA_LIST_FIELDS =
        "alternative_titles{en,ja},list_status{$MANGA_LIST_STATUS_FIELDS},num_chapters,num_volumes,media_type,status"
    const val MANGA_DETAIL_FIELDS =
        "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank," +
                "popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type," +
                "status,genres,my_list_status{$MANGA_LIST_STATUS_DETAIL_FIELDS},num_volumes,num_chapters,authors{first_name,last_name}," +
                "pictures,background,related_anime,related_manga,recommendations,serialization{name}"

}
