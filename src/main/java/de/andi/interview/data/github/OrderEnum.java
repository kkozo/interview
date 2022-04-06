package de.andi.interview.data.github;

public enum OrderEnum {
    
    ASC("asc"),
    DESC("desc");

    private String mappedOrder;
     OrderEnum(String sortKey) {
        this.mappedOrder = sortKey;
    }

    public String getMappedOrder() {
        return mappedOrder;
    }
}
