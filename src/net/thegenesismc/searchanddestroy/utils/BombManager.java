package net.thegenesismc.searchanddestroy.utils;

import net.thegenesismc.searchanddestroy.SND;

public class BombManager {

    private SND plugin;

    public BombManager(SND plugin) {
        this.plugin = plugin;
    }

    private boolean isRedFused;
    private boolean isBlueFused;

    public boolean isRedFused() {
        return isRedFused;
    }

    public void setRedFused(boolean isRedFused) {
        this.isRedFused = isRedFused;
    }

    public boolean isBlueFused() {
        return isBlueFused;
    }

    public void setBlueFused(boolean isBlueFused) {
        this.isBlueFused = isBlueFused;
    }
}
