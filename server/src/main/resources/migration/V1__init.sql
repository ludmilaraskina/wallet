CREATE TABLE public.user_wallet
(
  user_id bigint NOT NULL,
  amount_EUR numeric default 0 CHECK (price >= 0),
  amount_USD numeric default 0 CHECK (price > 0),
  amount_GBP numeric default 0 CHECK (price > 0),
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  CONSTRAINT context_pk PRIMARY KEY (bot_id)
)
  WITH (
    OIDS = FALSE
  )
  TABLESPACE pg_default;

ALTER TABLE public.balance OWNER to postgres;