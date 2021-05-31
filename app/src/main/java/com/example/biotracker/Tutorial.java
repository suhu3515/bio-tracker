package com.example.biotracker;

public class Tutorial {

    int tutorialId;

    String tutorialTitle, tutorialText, tutorialLink;

    public Tutorial(int tutorialId, String tutorialTitle, String tutorialText, String tutorialLink) {
        this.tutorialId = tutorialId;
        this.tutorialTitle = tutorialTitle;
        this.tutorialText = tutorialText;
        this.tutorialLink = tutorialLink;
    }

    public int getTutorialId() {
        return tutorialId;
    }

    public void setTutorialId(int tutorialId) {
        this.tutorialId = tutorialId;
    }

    public String getTutorialTitle() {
        return tutorialTitle;
    }

    public void setTutorialTitle(String tutorialTitle) {
        this.tutorialTitle = tutorialTitle;
    }

    public String getTutorialText() {
        return tutorialText;
    }

    public void setTutorialText(String tutorialText) {
        this.tutorialText = tutorialText;
    }

    public String getTutorialLink() {
        return tutorialLink;
    }

    public void setTutorialLink(String tutorialLink) {
        this.tutorialLink = tutorialLink;
    }
}
