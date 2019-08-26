# ProjectCalc
**Список команд:**
1) NEWPRODUCT <name>
2) PURCHASE <name> <amount> <price> <date> 
3) DEMAND <name> <amount> <price> <date> 
4) SALESREPORT <name> <date> 

Параметры состоят из одного слова  
Формат даты год-месяц-число

**Ответы на запрос:**  
1)OK  
2)ERROR  
3)Числовое значение  

**Данные для PostgreSQL:**  
database: warehouse  
port: 5432  
username: postgres  
password: 22021990  

**Таблицы:**  
**1)products**  
CREATE TABLE public.products  
(  
    id serial NOT NULL,  
    name bit varying NOT NULL,  
    PRIMARY KEY (id),  
    CONSTRAINT name UNIQUE (name)  
)  
WITH (  
    OIDS = FALSE  
)  
TABLESPACE pg_default;  

ALTER TABLE public.products  
    OWNER to postgres;
	
**2)purchase**  
CREATE TABLE public.purchase  
(  
    id serial NOT NULL,  
    product_id integer NOT NULL,  
    amount integer NOT NULL,  
    price integer NOT NULL,  
    date date NOT NULL,  
	remained integer NOT NULL,  
    PRIMARY KEY (id),  
    CONSTRAINT product_id FOREIGN KEY (product_id)  
        REFERENCES public.products (id) MATCH SIMPLE  
        ON UPDATE RESTRICT  
        ON DELETE RESTRICT  
)  
WITH (  
    OIDS = FALSE  
);  
TABLESPACE pg_default;  

ALTER TABLE public.purchase  
    OWNER to postgres;  
	
**3)demand**  
CREATE TABLE public.demand  
(  
    id serial NOT NULL,  
    product_id integer NOT NULL,  
    amount integer NOT NULL,  
    price integer NOT NULL,  
    date date NOT NULL,  
    PRIMARY KEY (id),  
    CONSTRAINT product_id FOREIGN KEY (product_id)  
        REFERENCES public.products (id) MATCH SIMPLE  
        ON UPDATE RESTRICT  
        ON DELETE RESTRICT  
)  
WITH (  
    OIDS = FALSE  
);  
TABLESPACE pg_default;  

ALTER TABLE public.demand  
    OWNER to postgres;  
	
**4)sales**  
CREATE TABLE public.sales  
(  
    id serial NOT NULL,  
    product_id integer NOT NULL,  
    number_of_sold integer NOT NULL,  
    price_purchase integer NOT NULL,  
    price_demand integer NOT NULL,  
    date_demand integer NOT NULL,  
    PRIMARY KEY (id),  
    CONSTRAINT product_id FOREIGN KEY (product_id)  
        REFERENCES public.products (id) MATCH SIMPLE  
        ON UPDATE RESTRICT  
        ON DELETE RESTRICT  
)  
WITH (  
    OIDS = FALSE  
)  
TABLESPACE pg_default;  

ALTER TABLE public.sales  
    OWNER to postgres;  
