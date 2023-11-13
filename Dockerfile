FROM clojure:lein-alpine AS frontend

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN lein cljsbuild once min

FROM nginx:1.25.3-alpine

COPY --from=frontend /usr/src/app/public /usr/share/nginx/html

COPY misc/nginx/nginx.conf /etc/nginx/nginx.conf
COPY misc/nginx/conf.d /etc/nginx/conf.d
