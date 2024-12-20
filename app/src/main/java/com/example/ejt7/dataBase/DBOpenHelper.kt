package com.example.ejt7.dataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.ejt7.R
import com.example.ejt7.contract.CineContract
import com.example.ejt7.contract.PeliculaContract
import com.example.ejt7.models.Cine
import com.example.ejt7.models.Ciudad
import com.example.ejt7.models.Pelicula


class DBOpenHelper private constructor(context: Context?):
    SQLiteOpenHelper(context, PeliculaContract.NOMBRE_BD, null, PeliculaContract.VERSION){

        companion object{
            private var dbOpen: DBOpenHelper? = null
            fun getInstance(context: Context?): DBOpenHelper?{
                if (dbOpen == null) dbOpen = DBOpenHelper(context)
                return dbOpen
            }
        }

    override fun onCreate(p0: SQLiteDatabase?) {
        try {
            if (p0 != null) {
                p0.execSQL(
                    "CREATE TABLE ${PeliculaContract.Companion.Entrada.TABLA}"
                            +"(${PeliculaContract.Companion.Entrada.IDCOL} integer primary key"
                            +",${PeliculaContract.Companion.Entrada.TITULOCOL} VARCHAR(20) NOT NULL"
                            +",${PeliculaContract.Companion.Entrada.DESCRIPCIONCOLC} VARCHAR(70) NOT NULL"
                            +",${PeliculaContract.Companion.Entrada.POSTERCOL} int NOT NULL"
                            +",${PeliculaContract.Companion.Entrada.TIMECOL} int NOT NULL"
                            +",${PeliculaContract.Companion.Entrada.YEARCOL} int NOT NULL"
                            +",${PeliculaContract.Companion.Entrada.COUNTRYCOL} VARCHAR(20) NOT NULL)"
                )
                p0.execSQL("CREATE TABLE ${CineContract.Entrada.TABLA}"
                                    +"(${CineContract.Entrada.ID} INTEGER PRIMARY KEY"
                                    +",${CineContract.Entrada.NOMBRE} VARCHAR(50)"
                                    +",${CineContract.Entrada.CIUDAD} VARCHAR(50)"
                                    +",${CineContract.Entrada.LATITUD} REAL"
                                    +",${CineContract.Entrada.LATITUD} REAL)")
                p0.execSQL("CREATE TABLE Pelicula_Cine(peli_id INTEGER, cine_id INTEGER);")
                inicializarBBDD(p0)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p0 != null) {
            p0.execSQL("DROP TABLE IF EXISTS ${PeliculaContract.Companion.Entrada.TABLA};")
            onCreate(p0)
        }
    }

    private fun inicializarBBDD(db: SQLiteDatabase){
        val listaPeliculas = cargarPeliculas()
        val listaCines = cargarCines()
        listaPeliculas.forEach { addMovie(db, it) }


        for (peli in listaPeliculas){
            db.execSQL(
                ("INSERT INTO ${PeliculaContract.Companion.Entrada.TABLA}("+
                        "${PeliculaContract.Companion.Entrada.TITULOCOL},"+
                        "${PeliculaContract.Companion.Entrada.DESCRIPCIONCOLC},"+
                        "${PeliculaContract.Companion.Entrada.POSTERCOL},"+
                        "${PeliculaContract.Companion.Entrada.TIMECOL},"+
                        "${PeliculaContract.Companion.Entrada.YEARCOL},"+
                        "${PeliculaContract.Companion.Entrada.COUNTRYCOL})"+
                        " VALUES ('${peli.title}','${peli.description}','${peli.poster}','${peli.time}','${peli.year}','${peli.country}');")
            )
        }
    }

    private fun addMovie(db: SQLiteDatabase, peli: Pelicula){
        db.execSQL(
            ("INSERT INTO ${PeliculaContract.Companion.Entrada.TABLA}("+
                    "${PeliculaContract.Companion.Entrada.TITULOCOL},"+
                    "${PeliculaContract.Companion.Entrada.DESCRIPCIONCOLC},"+
                    "${PeliculaContract.Companion.Entrada.POSTERCOL},"+
                    "${PeliculaContract.Companion.Entrada.TIMECOL},"+
                    "${PeliculaContract.Companion.Entrada.YEARCOL},"+
                    "${PeliculaContract.Companion.Entrada.COUNTRYCOL})"+
                    " VALUES ('${peli.title}','${peli.description}','${peli.poster}','${peli.time}','${peli.year}','${peli.country}');")
        )
    }

    private fun addCine(db: SQLiteDatabase, cine: Cine){
        db.execSQL(
            ("INSERT INTO ${CineContract.Entrada.TABLA}("+
                    "${CineContract.Entrada.NOMBRE},"+
                    "${CineContract.Entrada.CIUDAD},"+
                    "${CineContract.Entrada.LATITUD},"+
                    "${CineContract.Entrada.LONGITUD})"+
                    " VALUES ('${cine.nombreCine}','${cine.ciudad}','${cine.latitud}','${cine.longitud}');")
        )
    }

    private fun cargarPeliculas(): MutableList<Pelicula>{
        return mutableListOf(
            Pelicula(1,"La vida es bella", "Un padre judío-italiano que utiliza su imaginación y humor para proteger a su hijo de los horrores de un campo de concentración nazi, transformando la tragedia en un juego para mantener viva la esperanza.",
                R.drawable.la_vida_es_bella,116, 1997, "Italia"
            ),
            Pelicula(2,"El padrino", "El envejecido patriarca de una dinastía del crimen organizado transfiere el control de su imperio clandestino a su hijo reacio.",
                R.drawable.elpadrino,175, 1972, "Estados Unidos"
            ),
            Pelicula(3,"El caballero oscuro","Cuando la amenaza conocida como El Joker emerge de su pasado misterioso, causa estragos y caos en la gente de Gotham.",
                R.drawable.el_caballero_oscuro,152, 2008, "Estados Unidos"
            ),
            Pelicula(4,"Pulp Fiction","La vida de dos sicarios de la mafia, un boxeador, la esposa de un gánster y dos bandidos se entrelazan en cuatro historias de violencia y redención.",
                R.drawable.pulpfiction,153, 1994, "Estados Unidos"
            ),
            Pelicula(5,"El Señor de los Anillos","Gandalf y Aragorn lideran el mundo de los hombres contra el ejército de Sauron para distraerlo de Frodo y Sam mientras se acercan al Monte del Destino con el Anillo Único.",
                R.drawable.senhoranillos,201, 2003, "Nueva Zelanda"
            ),
            Pelicula(6,"Forrest Gump","Los presidios de Forrest Gump, un hombre con un coeficiente intelectual bajo, tienen lugar durante varios eventos históricos estadounidenses.",
                R.drawable.forrestgump,142, 1994, "Estados Unidos"
            ),
            Pelicula(7, "Origen","Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartición de sueños recibe la tarea inversa de plantar una idea en la mente de un director ejecutivo.",
                R.drawable.origen,148, 2010, "Estados Unidos"
            ),
            Pelicula(8,"El club de la lucha","Un oficinista insomne y un fabricante de jabón desmotivado forman un club de lucha clandestino que se convierte en algo mucho más grande.",
                R.drawable.clublucha,139, 1999, "Estados Unidos"
            ),
            Pelicula(9,"El Imperio Contraataca","Después de que los rebeldes sean brutalmente sobrepasados por el Imperio en el planeta helado Hoth, Luke Skywalker comienza su entrenamiento Jedi con Yoda, mientras sus amigos son perseguidos por Darth Vader.",
                R.drawable.imperiocontraataca,124, 1980, "Estados Unidos"
            ),
            Pelicula(10,"El bueno, el malo y el feo","Un cazarrecompensas se asocia con un hombre para encontrar una fortuna en oro enterrado en un cementerio remoto.",
                R.drawable.buenomalofeo,161, 1966, "Italia"
            ),
            Pelicula(11,"Matrix","Un hacker informático aprende de rebeldes misteriosos sobre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores.",
                R.drawable.matrix,131, 1999, "Estados Unidos"
            ),
            Pelicula(12,"Uno de los nuestros","La historia de Henry Hill y su vida en la mafia, abarcando su relación con su esposa Karen Hill y sus socios mafiosos Jimmy Conway y Tommy DeVito.",
                R.drawable.uno_de_los_nuestros,148, 1990, "Estados Unidos"
            ),
            Pelicula(13,"La lista de Schindler","En la Polonia ocupada por los alemanes durante la Segunda Guerra Mundial, Oskar Schindler se preocupa gradualmente por su fuerza laboral judía después de presenciar su persecución por parte de los nazis.",
                R.drawable.la_lista_de_schindler,195, 1993, "Estados Unidos"
            ),
            Pelicula(14,"Interestelar","Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento por asegurar la supervivencia de la humanidad.",
                R.drawable.interstellar,169, 2014, "Estados Unidos"
            ),
            Pelicula(15,"Parásitos","La codicia y la discriminación de clase amenazan la recién formada relación simbiótica entre la adinerada familia Park y el empobrecido clan Kim.",
                R.drawable.parasitos,132, 2019, "Corea del sur"
            ),
            Pelicula(16,"La milla verde","Las vidas de los guardias en el corredor de la muerte se ven afectadas por uno de sus reclusos: un hombre negro acusado de asesinato infantil y violación, pero que tiene un don misterioso.",
                R.drawable.la_milla_verde,180, 1999, "Estados Unidos"
            ),
            Pelicula(17,"Cadena perpetua","Dos hombres encarcelados establecen una fuerte amistad a lo largo de los años, encontrando consuelo y redención eventual a través de actos de decencia común.",
                R.drawable.cadena_perpetua,142, 1994, "Estados Unidos"
            ),
            Pelicula(18,"El pianista","Narra la historia real de Władysław Szpilman, un pianista judío-polaco que lucha por sobrevivir durante la ocupación nazi en la Segunda Guerra Mundial, utilizando su talento y resiliencia en medio del horror.",
                R.drawable.el_pianista,148, 2002, "Reino Unido"
            )
        )
    }

    private fun cargarCines(): MutableList<Cine> = mutableListOf(
        Cine(1,"Yelmo Plaza Mayor", Ciudad.Malaga, 36.657008541619696, -4.479436545364441),
        Cine(2, "Multicines Rosaleda", Ciudad.Malaga, 36.734400561026256, -4.430879285310845),
        Cine(3,"Yelmo Premium Lagoh", Ciudad.Sevilla, 37.342737154311536, -5.9807750892942195),
        Cine(4,"CINESA CAMAS", Ciudad.Sevilla, 37.3940780517548, -6.022246565874445),
        Cine(5, "Cines Axion", Ciudad.Cordoba, 37.87981038268583, -4.759757674924729),
        Cine(6, "Cine Delicias", Ciudad.Cordoba, 37.89143084391786, -4.762211615196308),
        Cine(7, "Yelmo Luxury Palafox", Ciudad.Madrid, 40.43105039331717, -3.6979526007704324),
        Cine(8, "Capitol Gran Via", Ciudad.Madrid, 40.42093615504402, -3.7083801550820104),
        Cine(9,"Cines Babel", Ciudad.Valencia, 39.473570437703515, -0.34953625577332254),
        Cine(10, "Yelmo Campanar", Ciudad.Valencia, 39.47420075502282, -0.3981217956135429),
        Cine(11, "Filmax Gran Via", Ciudad.Barcelona, 41.36155957214645, 2.134278325373307),
        Cine(12, "Yelmo Westfield La Maquinista", Ciudad.Barcelona, 41.44029997978995, 2.201834975278853),
        Cine(13,"Ocine Premium Los Fresnos", Ciudad.Gijon, 43.53373646323169, -5.6582220204697435),
        Cine(14, "Yelmo Ocimax", Ciudad.Gijon, 43.536778022943224, -5.690641601795603),
        Cine(15, "Cine Alcazar", Ciudad.Pamplona, 42.81474593421737, -1.639394371850273),
        Cine(16, "Yelmo Itaroa", Ciudad.Pamplona, 42.82913707016941, -1.57926348455501)
    )


}