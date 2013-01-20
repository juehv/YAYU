/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.util.container;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JUehV
 */
public enum Category {
    //FIXME umprogrammieren ... automatisiert erstellen

    Film, Autos, Music, Animals, Sports, Travel,  
    Games, Comedy, People, News, Entertainment, 
    Education, Howto, Nonprofit, Tech, Shows, Trailers;
    
    public static List<String> getAsList (){
        List<String> retval = new ArrayList<>();
        for (Category value : Category.values()){
            retval.add(value.toString());
        }
        return retval;
    }
}
