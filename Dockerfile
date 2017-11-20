FROM nginx
COPY public /usr/share/nginx/html
COPY misc/nginx/conf.d /etc/nginx/conf.d
COPY misc/nginx/nginx.conf /etc/nginx/nginx.conf
