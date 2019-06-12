CREATE TABLE public.user_wallet
(
  user_id bigint NOT NULL PRIMARY KEY,
  amount_EUR numeric default 0 CHECK (amount_EUR >= 0),
  amount_USD numeric default 0 CHECK (amount_USD >= 0),
  amount_GBP numeric default 0 CHECK (amount_GBP >= 0),
  created_at timestamp without time zone,
  updated_at timestamp without time zone
)
  WITH (
    OIDS = FALSE
  )
  TABLESPACE pg_default;

ALTER TABLE public.user_wallet OWNER to postgres;