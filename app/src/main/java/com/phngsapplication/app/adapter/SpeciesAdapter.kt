package com.phngsapplication.app.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SectionIndexer
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.Helpers
import com.phngsapplication.app.R
import com.phngsapplication.app.model.Species
import com.phngsapplication.app.model.SpeciesAlphabet
import com.phngsapplication.app.ui.SpeciesFragment
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class SpeciesAdapter (
    var list: List<SpeciesAlphabet>
    ) : RecyclerView.Adapter<SpeciesAdapter.RowDSSpeciesVH>(), SectionIndexer {

    private val mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#"
    private var sectionsTranslator = HashMap<Int, Int>()
    private var mSectionPositions: ArrayList<Int>? = null

    var onItemClick: ((Species)->Unit)? =null   //quan trong
    private lateinit var speciesFragment: SpeciesFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowDSSpeciesVH {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.row_ds_species,parent,false)
        return RowDSSpeciesVH(view)
    }

    override fun onBindViewHolder(holder: RowDSSpeciesVH, position: Int) {
        val item = list[position]
        holder.alphabet.setText(item.alphabet)

        val adapter = NameSpeciesAdapter(item.species)
        holder.recyclerView.adapter = adapter

        adapter.onItemClick = {
            onItemClick?.invoke(it)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getSections(): Array<String> {
        val sections: MutableList<String> = ArrayList(27)
        val alphabetFull = ArrayList<String>()
        mSectionPositions = ArrayList()
        run {
            var i = 0
            val size = list!!.size
            while (i < size) {
                val section = list[i].alphabet[0].toString().uppercase(Locale.getDefault())
                if (!sections.contains(section)) {
                    sections.add(section)
                    mSectionPositions?.add(i)
                }
                i++
            }
        }
        for (element in mSections) {
            alphabetFull.add(element.toString())
        }
        sectionsTranslator = Helpers.sectionsHelper(sections, alphabetFull)
        return alphabetFull.toTypedArray()
    }

    override fun getPositionForSection(sectionIndex: Int): Int {
        return mSectionPositions!![sectionsTranslator[sectionIndex]!!]
    }

    override fun getSectionForPosition(position: Int): Int {
        return 0
    }

    inner class RowDSSpeciesVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val alphabet: TextView = view.findViewById(R.id.textView)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    }
}