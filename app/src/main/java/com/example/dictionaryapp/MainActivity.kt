package com.example.dictionaryapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        answer.visibility = View.INVISIBLE
        thesaurus_button.visibility = View.INVISIBLE
        val searchWord : EditText = findViewById(R.id.search_word)
        val queue  = Volley.newRequestQueue(this)
        dictionary_button.setOnClickListener{
            val searchWord1 = searchWord.text
            val apiKey = "17b29b5e-9a96-46c1-a710-2d88dad3b925"
            val url =
                "https://www.dictionaryapi.com/api/v3/references/learners/json/$searchWord1?key=$apiKey"

            val stringRequest = StringRequest(Request.Method.GET , url ,
                { response ->
                    getJSONDefinitionDictionary(response)
                    var mean1 = getJSONDefinitionDictionary(response).toString()
                    mean1 = mean1.removePrefix("Def(def")
                    mean1 = mean1.replace("=",". ").removePrefix("(def1").replace("def2","\n2.").removeSuffix(")")
                    answer.text = mean1
                    answer.visibility = View.VISIBLE
                },
                {
                    Toast.makeText(this , "error", Toast.LENGTH_SHORT).show()
                    }
            )
            queue.add(stringRequest)
        }
        thesaurus_button.setOnClickListener {
            val searchWord1 = searchWord.text
            val apiKey = "3915a5b3-b1c9-4ca6-941f-a2eae35a7af1"
            val url =
                "https://www.dictionaryapi.com/api/v3/references/ithesaurus/json/$searchWord1?key=$apiKey"

            val stringRequest = StringRequest(Request.Method.GET , url ,
                { response ->
                    getJSONDefinitionThesaurus(response)
                    var synonyms = getJSONDefinitionThesaurus(response).toString()
                    synonyms = synonyms.replace("["," ").replace("]"," ").replace('"',' ')
                    answer.text = synonyms
                    answer.visibility = View.VISIBLE
                },
                {
                    Toast.makeText(this , "error", Toast.LENGTH_SHORT).show()
                }
            )
            queue.add(stringRequest)
        }


    }
    data class Def(var def1 : Any , var def2 : Any)
    private fun getJSONDefinitionDictionary(response : String): Def {
        val JSON_array = JSONArray(response)
        val firstIndex = JSON_array.getJSONObject(0)
        val getDefinition = firstIndex.getJSONArray("shortdef")
        val definition = getDefinition.get(0)
        val definition2 = getDefinition.get(1)
        return Def(" $definition ", " $definition2 " )

    }
    private fun getJSONDefinitionThesaurus(response : String): Any? {
        val JSONarray = JSONArray(response)
        val firstIndex = JSONarray.getJSONObject(0)
        val getDefinition = firstIndex.getJSONObject("meta")
        val definition = getDefinition.getJSONArray("syns")
        val synonym = definition.get(0)

        return synonym

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.choose,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.dictionary ->{
                dictionaryClicked()
                true
            }
            R.id.thesaurus ->{
                thesaurusClicked()
                true
            }
            R.id.developer ->{
                aboutDeveloperclicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun dictionaryClicked(){
        thesaurus_button.visibility = View.INVISIBLE
        dictionary_button.visibility = View.VISIBLE
        meaning_or_similar_words.setText(R.string.meaning)
    }
    private fun thesaurusClicked(){
        dictionary_button.visibility = View.INVISIBLE
        thesaurus_button.visibility = View.VISIBLE
        meaning_or_similar_words.setText(R.string.similar_words)

    }
    private fun aboutDeveloperclicked(){
        val intent = Intent(applicationContext,AboutDeveloperActivity::class.java)
        startActivity(intent)
    }
}