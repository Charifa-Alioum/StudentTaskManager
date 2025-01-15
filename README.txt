PROJET DE DÉVELOPPEMENT MOBILE : GROUPE 11 (Student Task Manager)

Le code fonctionnel de l'application se trouve sur la branche master du projet (la branche testBranch servait de branche sur laquelle les fonctionnalités de l'application étaient testées).
Le MainActivity contient six fragments : 
- AgendaFragment: où l'utilisateur entre ses événements importants dont il veut les rappels par notification.
- HomeworkFragment: où l'utilisateur peut faire un suivi de ses devoirs et cocher lorsqu'un devoir a été effectué.
- SubjectFragment: où l'utilisateur entre ses matières. Après les avoir entrées, en faisant un clic sur la matière, l'utilisateur ouvre une activité dans laquelle il renseigne les notes de la matière, et peut y laisser un commentaire.
- ModuleFragment: ici, l'utilisateur peut créer des modules (un module est caractérisé par un nom, un nombre de crédits et un ensemble de matières). Après la création du module, en faisant un clic sur le module, l'utilisateur ouvre une activité dans laquelle il peut ajouter les matières au module. En faisant un clic sur la matière dans l'activité, une boîte de dialogue affiche les éléments renseignés plus haut et calcule la moyenne de la matière. L'activité fille contient une vue qui affiche la moyenne du module, pareil pour ModuleFragment qui affiche la moyenne du semestre.
- TimetableFragment: qui affiche un CalendarView et en dessous, affiche les matières de la journée. 
- OverviewFragment: qui affiche les évènements de la journée actuelle et la journée du lendemain.

Les fragments fonctionnels sont:
- AgendaFragment qui ajoute des évènements et envoie des notifications. 
- HomeworkFragment qui ajoute des devoirs à la liste et permet de les cocher. Cependant, l'état des checkbox ne parvient pas à être sauvegardé et se remet toujours à l'état décoché. Un clic maintenu affiche une boîte de dialogue pour la suppression de l'élément.
- SubjectFragment qui permet de créer une matière et renseigner ses différentes notes. On peut également supprimer une matière grâce à un clic maintenu.
- ModuleFragment qui ajoute des matières à un module et calcule les diverses moyennes(d'une matière, d'un module et du semestre). Un long clic permet également la suppression ici.


DIFFICULTÉS RENCONTRÉES 

TimetableFragment affiche le CalendarView mais ne parvient pas à ajouter une matière à l'emploi du temps (un NullPointerException entraîne le crash de l'application)
OverviewFragment n'est pas opérationnel non plus car malgré le fait que AgendaFragment enregistre et envoie des notifications, il ne parvient pas à retourner les données stockées dans ses préférences partagées. Ce sont ces données que OverviewFragment doit afficher.

L'application peut également déposer un widget sur l'écran, cependant, ce widget ne parvient pas à renvoyer les informations de AgendaFragment.

CLASSES DU PACKAGE studenttaskmanagerfeatures
- AgendaItemWidgetProvider: cette classe est celle qui permet de poser un widget sur l'écran d'accueil de l'appareil Android.
- GradeCalculator: cette classe permet de calculer la moyenne d'une matière 
- NotificationReceiver: cette classe est celle qui permet d'envoyer des notifications à l'utilisateur.
- PreferenceManager: cette classe centralise la gestion des SharedPreferences de l'application 
- SubjectDialogFragment: cette classe affiche la boîte de dialogue qui récupère les informations d'une matière (ces informations sont entrées dans ChildItemsActivity du package studenttaskmanagerdetails) et affiche également la moyenne.
- SubjectViewModel: cette classe adapte la vue d'un objet de type SubjectItem pour TimetableFragment 

CLASSES DU PACKAGE studenttaskmanagerdetails 
- AgendaItem: c'est une classe de modèle pour les évènements dans AgendaFragment 
- AgendaAdapter: cette classe adapte les éléments de type AgendaItem pour m'afficher que le nom dans la liste
- ModuleItem: c'est une classe de modèle pour les évènements dans ModuleFragment 
- ModuleAdapter: cette classe adapte les éléments de type ModuleItem pour m'afficher que le nom dans la liste 
- HomeworkItem: c'est une classe de modèle pour les évènements dans HomeworkFragment 
- HomeworkAdapter: cette classe adapte les éléments de type HomeworkItem pour m'afficher que le nom dans la liste
- ScheduleItem: c'est une classe de modèle pour les évènements dans TimetableFragment 
- ScheduleAdapter: cette classe adapte les éléments de type ScheduleItem pour m'afficher que le nom dans la liste
- SubjectItem: c'est une classe de modèle pour les évènements dans SubjectFragment 
- SubjectAdapter: cette classe adapte les éléments de type SubjectItem pour m'afficher que le nom dans la liste
- AddEventDialogFragment 
- AddHomeworkActivity
- ChildItemsActivity: cette activité affiche les matières d'un module 
- ColorPickerActivity: cette activité permet de renseigner les informations d'une matière (les notes et un commentaire)
- DetailsDialog
- SubjectDetailsActivity
