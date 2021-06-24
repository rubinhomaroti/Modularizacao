package br.com.rubinhomaroti.modularizacao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import br.com.rubinhomaroti.domain.model.Carta
import br.com.rubinhomaroti.mobcomponents.CustomToast
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var btRecomecar: Button
    private lateinit var btProximaCarta: Button
    private lateinit var tvPontuacao: TextView
    private lateinit var ivCarta: ImageView

    private var cartas: MutableList<Carta> = mutableListOf()
    private val gerador = Random()
    private fun getBaralho(): MutableList<Carta> {
        return mutableListOf<Carta>(
            Carta(R.drawable.as_de_espada, 1),
            Carta(R.drawable.dois_de_espada, 2),
            Carta(R.drawable.tres_de_espada, 3),
            Carta(R.drawable.quatro_de_espada, 4),
            Carta(R.drawable.cinco_de_espada, 5),
            Carta(R.drawable.seis_de_espada, 6),
            Carta(R.drawable.sete_de_espada, 7),
            Carta(R.drawable.oito_de_espada, 8),
            Carta(R.drawable.nove_de_espada, 9),
            Carta(R.drawable.dez_de_espada, 10),
            Carta(R.drawable.valete_de_espada, 10),
            Carta(R.drawable.dama_de_espada, 10),
            Carta(R.drawable.rei_de_espada, 10)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()
        setListeners()
        iniciarPartida()
    }

    private fun setUpView() {
        btRecomecar = findViewById(R.id.btRecomecar)
        btProximaCarta = findViewById(R.id.btProximaCarta)
        tvPontuacao = findViewById(R.id.tvPontuacao)
        ivCarta = findViewById(R.id.ivCarta)
    }

    private fun setListeners() {
        btProximaCarta.setOnClickListener {
            realizarJogada()
        }
        btRecomecar.setOnClickListener {
            iniciarPartida()
        }
    }

    private fun iniciarPartida() {
        tvPontuacao.text = "0"
        cartas = getBaralho()
        ivCarta.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo))
    }

    private fun realizarJogada() {
        val posicaoCartaSelecionada = gerador.nextInt(cartas.size)
        val cartaSelecionada = cartas.get(posicaoCartaSelecionada)
        val pontuacaoAtualizada = tvPontuacao.text.toString().toInt() + cartaSelecionada.pontuacao
        tvPontuacao.text = pontuacaoAtualizada.toString()
        exibeMensagem(pontuacaoAtualizada)
        if (pontuacaoAtualizada > 21) {
            iniciarPartida()
        } else {
            cartas.removeAt(posicaoCartaSelecionada)
            ivCarta.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    cartaSelecionada.resourceId
                )
            )
        }
    }

    private fun exibeMensagem(pontuacao: Int) {
        when {
            pontuacao == 21 -> {
                CustomToast.success(this, "Você fez 21! Parabéns! :D")
            }
            pontuacao > 21 -> {
                CustomToast.error(this, "Você fez $pontuacao e perdeu... :(")
            }
            pontuacao > 11 -> {
                CustomToast.warning(this, "Está quase em 21... tome cuidado agora! :O")
            } else -> {
                CustomToast.info(this, "Ainda está tranquilo, pegue a próxima carta. :)")
            }
        }
        controlarBotoes()
    }

    private fun controlarBotoes() {
        habilitarBotoes(false)
        Handler().postDelayed({
            habilitarBotoes(true)
        }, 2500)
    }

    private fun habilitarBotoes(habilitar: Boolean) {
        btProximaCarta.isEnabled = habilitar
        btRecomecar.isEnabled = habilitar
    }
}