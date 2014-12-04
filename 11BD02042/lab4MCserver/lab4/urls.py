# -*- coding: utf-8 -*-
from django.conf.urls import include, patterns, url
from lab4.views import *

urlpatterns = patterns('',
    # models
    url(r'^getUsersList/', getUsersList, name='get_users_list'),
    url(r'^setUser/', setUser, name='set_user'),
    url(r'^editUser/', editUser, name='edit_user'),
    url(r'^getUser/', getUser, name='get_user'),
    url(r'^deleteUser/', deleteUser, name='delete_user'),

)
