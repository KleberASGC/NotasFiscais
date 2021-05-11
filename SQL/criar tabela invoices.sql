CREATE TABLE public.invoices (
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	"number" int4 NOT NULL,
	model varchar(255) NULL,
	serie varchar(255) NOT NULL,
	cfop int4 NOT NULL,
	nfe_key varchar(255) NOT NULL,
	amount float8 NOT NULL,
	bc_icms_amount float8 NOT NULL,
	icms_amount float8 NOT NULL,
	bc_icms_st_amount float8 NOT NULL,
	icms_st_amount float8 NOT NULL,
	ecf_serial_number varchar(255) NULL,
	document_type varchar(255) NOT NULL,
	ref_nfe varchar(255) NULL,
	operation_nature varchar(255) NOT NULL,
	additional_data varchar(5000) NULL,
	arquivo_nfe xml NOT NULL,
	CONSTRAINT invoices_nfe_key_key UNIQUE (nfe_key)
	
);