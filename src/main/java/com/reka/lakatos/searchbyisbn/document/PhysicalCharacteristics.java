package com.reka.lakatos.searchbyisbn.document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhysicalCharacteristics {
    private float width;
    private float height;
    private float thickness;
    private CoverType coverType;
}
