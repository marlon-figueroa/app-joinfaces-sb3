package com.app.service;

import com.app.model.entity.Ruta;

import java.util.List;
import java.util.Map;

public interface IRutaService {

    List<Ruta> listar();

    Map<String, List<String>> getRutaRolMappings();

}
