FROM nginx
COPY public /usr/share/nginx/html
COPY misc/nginx/nginx.conf /etc/nginx/nginx.conf
