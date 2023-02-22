package com.inditex.zboost.service;

import com.inditex.zboost.entity.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RankingServiceImpl implements RankingService {
    private OrderService orderService;

    public RankingServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Ranking ranking(RankingType rankingType, Date fromDate, Date toDate) {
        /**
         * TODO: EJERCICIO 4. Implementacion del Ranking
         *
         * Hasta ahora hemos ido resolviendo los ejercicios principalmente lanzando consultas SQL contra nuestra base de
         * datos. Creemos que es hora de ver tus skills de programacion, asi que este ejercicio debe resolverse de forma
         * puramente programatica sin realizar ninguna consulta directa contra la BD ;)
         *
         * PISTAS:
         * 1- El ejercicio puede resolverse utilizando unicamente el {@link OrderService} inyectado.
         * 2- Para ayudarte en el ejercicio, haz uso del metodo {@link OrderService#findOrdersBetweenDates(Date, Date)} ya 
         * implementado para recuperar aquellos pedidos realizados entre dos fechas.
         * 3- Puedes hacer uso del metodo de utilidad {@link com.inditex.zboost.utils.Converters#toProductRank(ProductOrderItem)}
         * para convertir mapear los campos basicos de un producto de un {@link ProductOrderItem} a un {@link ProductRank}
         * 4- Recuerda que {@link ProductOrderItem} es una extension de {@link Product}, el cual tiene implementado el 
         * metodo hashcode que puede resultarte muy util ;)
         *
         * BONUS TRACK: Se valorara positivamente la resolucion del ejercicio mediante el API de Streams de Java
         *
         * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/package-summary.html
         * https://www.baeldung.com/java-8-streams
         */

        return new Ranking();
    }

}
