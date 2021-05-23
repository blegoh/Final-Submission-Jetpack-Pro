package me.yusufeka.moviecatalogue.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.yusufeka.moviecatalogue.movies.model.Genre
import me.yusufeka.moviecatalogue.movies.model.ProductionCompany
import me.yusufeka.moviecatalogue.movies.model.ProductionCountry
import me.yusufeka.moviecatalogue.movies.model.SpokenLanguage
import me.yusufeka.moviecatalogue.tvseries.models.*
import java.lang.reflect.Type

class DataConverter {

    @TypeConverter
    fun fromIntList(intList: List<Int>?): String? {
        val gson = Gson()
        intList?.let {
            return gson.toJson(intList)
        }
        return null
    }

    @TypeConverter
    fun toIntList(json: String?): List<Int>? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<List<Int>>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    @TypeConverter
    fun fromStringList(strings: List<String>?): String? {
        val gson = Gson()
        strings?.let {
            return gson.toJson(strings)
        }
        return null
    }

    @TypeConverter
    fun toStringList(json: String?): List<String>? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<List<String>>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    @TypeConverter
    fun fromGenreList(genres: List<Genre>?): String? {
        val gson = Gson()
        genres?.let {
            return gson.toJson(genres)
        }
        return null
    }

    @TypeConverter
    fun toGenreList(json: String?): List<Genre>? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<List<Genre>>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    @TypeConverter
    fun fromProductionCountryList(productionCountries: List<ProductionCountry>?): String? {
        val gson = Gson()
        productionCountries?.let {
            return gson.toJson(productionCountries)
        }
        return null
    }

    @TypeConverter
    fun toProductionCountryList(json: String?): List<ProductionCountry>? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<List<ProductionCountry>>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    @TypeConverter
    fun fromSpokenLanguageList(spokenLanguages: List<SpokenLanguage>?): String? {
        val gson = Gson()
        spokenLanguages?.let {
            return gson.toJson(spokenLanguages)
        }
        return null
    }

    @TypeConverter
    fun toSpokenLanguageList(json: String?): List<SpokenLanguage>? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<List<SpokenLanguage>>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    @TypeConverter
    fun fromSeasonList(seasons: List<Season>?): String? {
        val gson = Gson()
        seasons?.let {
            return gson.toJson(seasons)
        }
        return null
    }

    @TypeConverter
    fun toSeasonList(json: String?): List<Season>? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<List<Season>>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    @TypeConverter
    fun fromCreatedByList(createdByList: List<CreatedBy>?): String? {
        val gson = Gson()
        createdByList?.let {
            return gson.toJson(createdByList)
        }
        return null
    }

    @TypeConverter
    fun toCreatedByList(json: String?): List<CreatedBy>? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<List<Season>>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    @TypeConverter
    fun fromProductionCompanyList(productionCompanies: List<ProductionCompany>?): String? {
        val gson = Gson()
        productionCompanies?.let {
            return gson.toJson(productionCompanies)
        }
        return null
    }

    @TypeConverter
    fun toProductionCompanyList(json: String?): List<ProductionCompany>? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<List<ProductionCompany>>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    @TypeConverter
    fun fromNetworkList(networks: List<Network>?): String? {
        val gson = Gson()
        networks?.let {
            return gson.toJson(networks)
        }
        return null
    }

    @TypeConverter
    fun toNetworkList(json: String?): List<Network>? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<List<Network>>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    @TypeConverter
    fun fromGuestStarList(guestStars: List<GuestStar>?): String? {
        val gson = Gson()
        guestStars?.let {
            return gson.toJson(guestStars)
        }
        return null
    }

    @TypeConverter
    fun toGuestStarList(json: String?): List<GuestStar>? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<List<GuestStar>>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    @TypeConverter
    fun fromLastEpisodeToAir(lastEpisodeToAir: LastEpisodeToAir?): String? {
        val gson = Gson()
        lastEpisodeToAir?.let {
            return gson.toJson(lastEpisodeToAir)
        }
        return null
    }

    @TypeConverter
    fun toLastEpisodeToAir(json: String?): LastEpisodeToAir? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<LastEpisodeToAir>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

}