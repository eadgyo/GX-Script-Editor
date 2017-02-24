package org.eadge.model.frame.global.project;

import java.util.Observable;

/**
 * Created by eadgyo on 24/02/17.
 *
 * Allows Selection model to be observable
 */

public class SelectionObservable extends Observable
{
    public void callObservers()
    {
        setChanged();
        notifyObservers();
    }
}
