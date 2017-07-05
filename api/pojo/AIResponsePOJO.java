package com.buysmart.api.pojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AIResponsePOJO {

	private String status;

	private String usage;

	private Taxonomy[] taxonomy;

	private String language;

	private String url;

	private String warningMessage;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public Taxonomy[] getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(Taxonomy[] taxonomy) {
		this.taxonomy = taxonomy;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	@Override
	public String toString() {
		return "ClassPojo [status = " + status + ", usage = " + usage + ", taxonomy = " + taxonomy + ", language = "
				+ language + ", url = " + url + ", warningMessage = " + warningMessage + "]";
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Taxonomy {
		private String score;

		private String label;

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return "ClassPojo [score = " + score + ", label = " + label + "]";
		}
	}

}
