# Changelog

## [1.0.0] - 2026-08-29

### Features
- add store referral get/create and referral apply endpoints
- add JavaDoc, health aliases, and component tests

### Bug Fixes
- update OfferServiceTest to use UUID ids after Offer.id type change
- change Offer id from String to UUID to match PostgreSQL uuid column
- merge offer pool fix into stage
- restore minimum-idle=0 to stay within Cloud SQL 25-conn limit
- add POST /offers/validate endpoint for coupon validation
- add store-scoped offer endpoints to resolve 500 on /api/offers
- remove liquibase default-schema to allow fresh DB bootstrap
- run create-schema always so it recreates if missing
- accept any checksum for idempotent create-schema changeset
- limit HikariCP pool to 2 connections (db-f1-micro max 25 total)
- disable Hibernate validation (Liquibase owns schema, uuid vs String mismatch)
- set liquibase-schema=public so schema is created before tracking tables
- add Cloud SQL postgres-socket-factory for Cloud Run connectivity

### Documentation
- add complete project documentation

### CI/Build
- retrigger after db-g1-small upgrade
- trigger first dev build
- use separate GCP project IDs for dev (digi-carts-dev) and prod (digi-carts)