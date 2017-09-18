package com.ringlabskiev.api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoverPhotos {

    @JsonProperty("photos")
    public List<Photo> photos = null;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Photo {
        public Integer id;
        public Integer sol;
        public Camera camera;
        @JsonProperty("img_src")
        public String imgSrc;
        @JsonProperty("earth_date")
        public String earthDate;
        public Rover rover;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Rover {

        public Integer id;
        public String name;
        @JsonProperty("landing_date")
        public String landingDate;
        @JsonProperty("launch_date")
        public String launchDate;
        public String status;
        @JsonProperty("max_sol")
        public Integer maxSol;
        @JsonProperty("max_date")
        public String maxDate;
        @JsonProperty("total_photos")
        public Integer totalPhotos;
        @JsonProperty("cameras")
        public List<Camera_> cameras = null;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Camera {
        public Integer id;
        public String name;
        @JsonProperty("rover_id")
        public Integer roverId;
        @JsonProperty("full_name")
        public String fullName;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Camera_ {
        public String name;
        @JsonProperty("full_name")
        public String fullName;

    }
}
