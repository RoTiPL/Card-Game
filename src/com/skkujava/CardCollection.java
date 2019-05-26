package com.skkujava;

import java.util.ArrayList;
import java.util.Arrays;

public class CardCollection {
    public static final ArrayList<Card> warriorCollection = new ArrayList<>(
            Arrays.asList(
                    new Strike(),
                    new Block()
            )
    );

    public static final ArrayList<Card> thiefCollection = new ArrayList<>(
            Arrays.asList(
                    new Strike(),
                    new Block()
            )
    );
}
