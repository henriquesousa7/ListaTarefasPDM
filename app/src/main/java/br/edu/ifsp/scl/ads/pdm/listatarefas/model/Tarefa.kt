package br.edu.ifsp.scl.ads.pdm.listatarefas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Tarefa(
        val titulo: String = "",
        val descricao: String = "",
        val usuario: String = "",
        val dataCriacao: String = "",
        val dataPrevista: String = "",
        var statusTarefa: String = "",
        var usuarioConcluiu: String = "",
) : Parcelable