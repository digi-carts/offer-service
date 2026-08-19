# offer-service

Discount codes and coupons. Port **3010**, schema **`offer_svc`**.

Platform design: [System design](https://github.com/digi-carts/doc/blob/main/architecture/system-design.md)

## Domain

An `Offer` has a `code`, `type` (percent vs amount — string in DB), `scope` (default `PRODUCT`), numeric `value`, optional `max_uses` / `used_count`, `expires_at`, `ref_code`, `min_order_amt`, optional `store_id`, and `active`.

Checkout should call `POST /api/offers/{id}/use` to increment usage.

## Tech stack

Java 21, Spring Boot 3.3.0, Web, JPA, Validation, Liquibase, PostgreSQL.

## HTTP API

Gateway and controller share `/api/offers/**`.

| Method | Path | Notes |
|--------|------|--------|
| GET | `/api/offers` | `?storeId=` and/or `?active=true` |
| GET | `/api/offers/{id}` | |
| GET | `/api/offers/code/{code}` | Lookup at checkout |
| POST | `/api/offers` | `OfferRequest` |
| PUT | `/api/offers/{id}` | |
| DELETE | `/api/offers/{id}` | 204 |
| POST | `/api/offers/{id}/use` | Increment `used_count` |

### Health

`GET /health`

Headers: `X-User-Id`, `X-User-Role`.

## Configuration

| Variable | Required | Default |
|----------|----------|---------|
| `DATABASE_URL` | yes | schema `offer_svc` |
| `PORT` | no | `3010` |

## Local run

```bash
export DATABASE_URL="jdbc:postgresql://localhost:5432/digicarts?currentSchema=offer_svc"
mvn spring-boot:run
```

## CI/CD

`digi-cart-offer-service-dev` / `digi-cart-offer-service`.

## Related

- [order-service](https://github.com/digi-carts/order-service/blob/stage/doc/README.md)
- [billing-service](https://github.com/digi-carts/billing-service/blob/stage/doc/README.md) (`coupon_discount`)
- [merchant-ui](https://github.com/digi-carts/merchant-ui/blob/stage/doc/README.md) settings/discounts

## REST API reference

See [api.md](api.md) for every HTTP endpoint generated from Spring controllers.
