package com.out.workout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailModel {
        @SerializedName("full_description")
        @Expose
        private String fullDescription;
        @SerializedName("header")
        @Expose
        private String header;
        @SerializedName("image")
        @Expose
        private String image;

        public String getFullDescription() {
            return fullDescription;
        }

        public void setFullDescription(String fullDescription) {
            this.fullDescription = fullDescription;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(DetailModel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("fullDescription");
            sb.append('=');
            sb.append(((this.fullDescription == null)?"<null>":this.fullDescription));
            sb.append(',');
            sb.append("header");
            sb.append('=');
            sb.append(((this.header == null)?"<null>":this.header));
            sb.append(',');
            sb.append("image");
            sb.append('=');
            sb.append(((this.image == null)?"<null>":this.image));
            sb.append(',');
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }
}
