package com.eryalus.emptybot.i18n;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class StringManager {

    /**
     * Language fallback list. From left to right. 
     * 
     * Example: {"es", "en"} and "es" is selected.
     * The application will try to find the translation on the "es" translations.
     * If the translation does not exist on "es" it'll try to find it on "en".
     */
    private static final List<String> LANG_FALLBACK = Arrays.asList(new String[]{"es", "en"});

    /**
     * Default lang. It'll be used when an instance is asked without lang.
     */
    private static final String DEFAULT_LANGUAGE = "en";

    /**
     * Map of translation instances with the language as key.
     */
    private static final Map<String,StringManager> instances = new HashMap<>();

    /**
     * The loaded json.
     */
    private final JsonNode rootNode;

    /**
     * The current lang.
     */
    private final String currentLang;

    /**
     * True if lang fallback is enabled.
     */
    private final boolean fallbackEnabled;

    /**
     * Translation constructor. 
     * 
     * @param lang the language.
     * @param fallback whether the fallback is enabled or not.
     */
    private StringManager(String lang, boolean fallback){
        this.currentLang = lang;
        this.fallbackEnabled = fallback;
        InputStream in = StringManager.class.getResourceAsStream("../../../../i18n/"+lang+".json");
        if(in == null){
            // not a single translation file could be found
            throw new RuntimeException("No translation file could be loaded for lang "+lang);
        }
        try {
            rootNode = new ObjectMapper().readTree(in);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Translation file for lang "+lang+" is not parseable");
        }
    }

    /**
     * Get the translate instance for {@code DEFAULT_LANGUAGE}.
     * 
     * @return Translation instance.
     */
    public static StringManager getInstance(){
        return getInstance(DEFAULT_LANGUAGE);
    }
    
    /**
     * Get the translate instance for the given language.
     * 
     * @param lang the language.
     * @return Translation instance.
     */
    public static StringManager getInstance(String lang){
        return getInstance(lang, true);
    }

    /**
     * Get the translate instance for the given language managing fallback behavior.
     * 
     * @param lang the language.
     * @param enableFallback whether the fallback is enabled or not.
     * @return Translation instance.
     */
    public static StringManager getInstance(String lang, boolean enableFallback){
        lang = lang != null && !lang.equals("") ? lang : DEFAULT_LANGUAGE;
        StringManager instance = instances.get(lang);
        if(instance == null){
            instance = new StringManager(lang, enableFallback);
        }
        return instance;
    }

    /**
     * Get a translation based on the given key. 
     * <p>
     * Other langs will be tested if the translation fails if fallback is enabled.
     * They'll follow the {@code StringManager.LANG_FALLBACK} order.
     * </p>
     * 
     * @param key The translation key.
     * @return The translation.
     */
    public String translate(String key){
        return this.translate(key, null);
    }

    /**
     * Get a translation based on the given key. 
     * <p>
     * Other langs will be tested if the translation fails if fallback is enabled.
     * They'll follow the {@code StringManager.LANG_FALLBACK} order.
     * </p>
     * <p>
     * Parameters can be applied too using the {@code params}.
     * In-translation parameters are referenced by its key using double curly braces <code>{{ key }}</code>.
     * This references will be replaced by its value.
     * <p>
     * For example:
     * key-value -> <code>{"username", "John Doe"}</code>
     * and translation <code>"Welcome, {{ username }}"</code>
     * will result on <code>"Welcome, John Doe"</code>.
     * 
     * </p>
     * </p>
     * @param key The translation key.
     * @param params Map of parameters to be changed.
     * @return The translation.
     */
    public String translate(String key, Map<String,String> params){
        String[] subKeys = key.split("\\.");
        JsonNode node = rootNode;
        for(String k: subKeys){
            node = node.get(k);
            if(node == null){
                break;
            }
        }

        if(node != null && node.isTextual()){
            String text = node.asText();
            if(params != null){
                text = applyParams(text, params);
            }
            return text;
        }else{
            // if no translation is found go fallback if enabled
            if(fallbackEnabled){
                int fallbackIndex = LANG_FALLBACK.lastIndexOf(currentLang) + 1;
                if(fallbackIndex<LANG_FALLBACK.size()){
                    return getInstance(LANG_FALLBACK.get(fallbackIndex)).translate(key, params);
                }
            }
            return key;
        }
    }

    /**
     * Apply the params to the text.
     * 
     * @param text the original text.
     * @param params the params map.
     * @return text with the keys replaced.
     */
    private String applyParams(String text, Map<String,String> params){
        for(String key: params.keySet()){
            text = text.replaceAll("\\{\\{[ ]*"+key+"[ ]*\\}\\}", params.get(key));
        }
        return text;
    }
}
