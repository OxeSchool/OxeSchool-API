package com.oxeschool.api.entity.Curso;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Aulas{

    private UUID id;
    private String titulo;
    private String texto;
    private String videoUrl;

}
