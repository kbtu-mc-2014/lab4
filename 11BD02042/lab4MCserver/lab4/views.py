from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from lab4.models import *
import json
# Create your views here.

def getUsersList(request):
    results = {}
    data = []
    for user in Users.objects.all():
        tmp = {}
        tmp['id'] = user.id
        tmp['name'] = user.name
        tmp['surname'] = user.surname
        data.append(tmp)
    results['Users'] = data
    return JsonResponse(data=results)

def setUser(request):
    name = request.GET['name']
    surname = request.GET['surname']
    student = Users(name=name, surname=surname)
    student.save()
    json_data = {"success":True}
    return JsonResponse(data=json_data)

def editUser(request):
    id = request.GET['id']
    newName = request.GET['name']
    newSurname = request.GET['surname']
    student = Users.objects.get(id=id)
    student.name = newName
    student.surname = newSurname
    student.save()
    json_data = {"success":True}
    return JsonResponse(data=json_data)

def getUser(request):
    id = request.GET['id']
    student = Users.objects.get(id=id)
    name = student.name
    surname = student.surname
    json_data = {"name":name, "surname":surname }
    return JsonResponse(data=json_data)

def deleteUser(request):
    id = request.GET['id']
    student = Users.objects.get(id=id)
    student.delete()
    name = student.name
    surname = student.surname
    json_data = {"success":True }
    return JsonResponse(data=json_data)