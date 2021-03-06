package webserver.responses;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;

import webserver.pojo.DatasetResources;

public class DocumentListAllpzd {

	Set<TopDoc> docs = null;

	public DocumentListAllpzd(DatasetResources datasetResources, int topicid, int nbdoc) throws IOException {

		Reader reader = new InputStreamReader(new FileInputStream(datasetResources.getTopdocTopicPairFile().replaceAll("pairs", "pzd")));
		docs = new TreeSet<>();

		try {
			JsonObject jsonObject = Json.parse(reader).asObject();
			JsonArray topics = jsonObject.get("topics").asArray();

			JsonObject topic = topics.get(topicid).asObject();
			JsonArray documents = topic.get("docs").asArray();
			
			for (int i = 0; i < nbdoc; i++) {
				JsonObject document = documents.get(i).asObject();
				String tretriev =document.get("text").asString();
				String nam = document.get("name").asString();
				String txt = tretriev;
				String aut = document.get("author").asString();
				String tim = document.get("time").asString();
				Double pro = Double.valueOf(document.get("proba").asString());
				TopDoc newDoc = new TopDoc(nam, txt, aut, tim, pro);
				int taille = docs.size();
				docs.add(newDoc);
				if(taille==docs.size()){
					nbdoc++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
	}

	class TopDoc implements Comparable<TopDoc> {
		String name;
		String text;
		String author;
		String time;
		double proba;

		public TopDoc(String name, String text, String author, String time, double proba) {
			super();
			this.name = name;
			this.text = text;
			this.author = author;
			this.time = time;
			this.proba = proba;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public double getProba() {
			return proba;
		}

		public void setProba(double proba) {
			this.proba = proba;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((author == null) ? 0 : author.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			long temp;
			temp = Double.doubleToLongBits(proba);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((text == null) ? 0 : text.hashCode());
			result = prime * result + ((time == null) ? 0 : time.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TopDoc other = (TopDoc) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (author == null) {
				if (other.author != null)
					return false;
			} else if (!author.equals(other.author))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (Double.doubleToLongBits(proba) != Double.doubleToLongBits(other.proba))
				return false;
			if (text == null) {
				if (other.text != null)
					return false;
			} else if (!text.equals(other.text))
				return false;
			if (time == null) {
				if (other.time != null)
					return false;
			} else if (!time.equals(other.time))
				return false;
			return true;
		}

		private DocumentListAllpzd getOuterType() {
			return DocumentListAllpzd.this;
		}

		@Override
		public int compareTo(TopDoc o) {
			if (this.equals(o)) {
				return 0;
			}

			if (proba < o.getProba()) {
				return 1;
			} else if (proba > o.getProba()) {
				return -1;
			}
			return text.compareTo(o.getText());
		}
		
		public String toString() {
			return text;
		}

	}

}
