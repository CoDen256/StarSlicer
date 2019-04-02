from tkinter import *
from math import *


class GrowthResolver:
	def __init__(self, init, total, type):
		self.init = init
		self.total = total
		self.type = type

	def get_rate(self, n, type):
		if type == "Exponential":
			return self.get_exp(n)
		elif type == "Polynomial":
			return self.get_pol(n)
		else:
			return "UNDEFINED"

	def __str__(self):
		return "Init: {}, Total:{}, Type:{}".format(self.init, self.total, self.type)

	def get_exp(self, n):
		try:
			return (self.total / self.init) ** (1 / n)
		except:
			print("!Unable to get Exponential Rate")
			print("!Total: {}, Init:{}, Num:{}".format(self.total, self.init, n))
			return "N/A"

	def get_pol(self, n):
		try:
			return (self.total - self.init) / n
		except:
			print("!Unable to get Polynomial Rate")
			print("!Total: {}, Init:{}, Num:{}".format(self.total, self.init, n))
			return "N/A"


class EntryHolder:
	def __init__(self, entry1, entry2, entry3):
		self.entry1 = entry1
		self.entry2 = entry2
		self.entry3 = entry3

	def get_all(self):
		return self.get_entry1(), self.get_entry2(), self.get_entry3()

	def get_entry1(self):
		try:
			return float(self.entry1.get())
		except:
			print('!Unable to cast entry1')
			return 0

	def get_entry2(self):
		try:
			return float(self.entry2.get())
		except:
			print('!Unable to cast entry2')
			return 0

	def get_entry3(self):
		return str(self.entry3.get())


class Application(Frame):
	"""docstring for Application"""

	def __init__(self, master):
		super(Application, self).__init__(master)

		self.grid()
		self.create_widgets()

	def createProperty(self, text, r, c=0):
		types = ["Exponential", "Polynomial"]

		Label(self, text=text).grid(row=r, column=c, sticky=W)
		initEntry = Entry(self)
		initEntry.grid(row=r, column=c + 1, sticky=W)
		Label(self, text="  to").grid(row=r, column=c + 2, sticky=W)
		endEntry = Entry(self)
		endEntry.grid(row=r, column=c + 3, sticky=W)

		typeEntry = StringVar()
		typeEntry.set(types[0])

		self.createRadioButtons("GrowthType:", typeEntry, types, r, c + 4)

		return initEntry, endEntry, typeEntry

	def createRadioButtons(self, text, var, options, r, c):
		Label(self, text=text).grid(row=r, column=c, sticky=W)
		for o in options:
			Radiobutton(self, text=o, variable=var, value=o).grid(row=r, column=c + 1, sticky=W)
			c += 1

	def create_widgets(self):
		self.countWith = StringVar()
		self.countWith.set('MaxNumber')
		self.createRadioButtons("Compute By", self.countWith, ['MaxNumber', 'Lifespan'], 0, 0)

		self.first = EntryHolder(*self.createProperty("MaxNumber/Lifespan", 2))
		self.second = EntryHolder(*self.createProperty("Amount per spawn", 3))
		self.third = EntryHolder(*self.createProperty("Period", 4))
		self.fourth = EntryHolder(*self.createProperty("Delay", 5))

		Label(self, text="Init Wave: ").grid(row=6, column=0, sticky=W)
		self.init_ent = Entry(self, width=5)
		self.init_ent.grid(row=6, column=1, sticky=W)

		Label(self, text="  to").grid(row=6, column=2, sticky=W)
		self.end_ent = Entry(self, width=5)
		self.end_ent.grid(row=6, column=3, sticky=W)

		self.hasContainer = BooleanVar()
		Checkbutton(self, text="Has Container", variable=self.hasContainer).grid(row=7, column=1, sticky=W)

		Button(self, text="Compute", command=self.computeWave).grid(row=8, column=0, sticky=W)
		Button(self, text="Format", command=self.format).grid(row=8, column=1, sticky=W)

		self.result = Text(self, width=100, height=30, wrap=WORD)
		self.result.grid(row=9, column=0, columnspan=4)

		Label(self, text='Log:').grid(row=8, column=5, sticky=W)
		self.error = Text(self, width=30, height=30, wrap=WORD)
		self.error.grid(row=9, column=5, columnspan=1)

	def compute(self):
		self.error_message = "Errors:\n"
		print("Computing...")

		self.isContainer = self.hasContainer.get()
		self.init_wave = 0
		try:
			self.init_wave = int(self.init_ent.get())
		except:
			self.error_message += "Unable to cast init_wave"
			print("Unable to cast init_wave")

		self.end_wave = 0
		try:
			self.end_wave = int(self.end_ent.get())
		except:
			self.error_message += "Unable to cast end_wave"
			print("Unable to cast end_wave")

		print("Inializing amount")
		self.amount = GrowthResolver(*self.second.get_all())
		print("Inializing period")
		self.period = GrowthResolver(*self.third.get_all())
		print("Inializing delay")
		self.delay = GrowthResolver(*self.fourth.get_all())

		print("Inializing maxnumber and lifespan")
		if self.countWith.get() == "MaxNumber":
			self.max_number = GrowthResolver(*self.first.get_all())
			self.lifespan = GrowthResolver(
				self.compute_lifespan(self.period.init, self.max_number.init, self.amount.init),
				self.compute_lifespan(self.period.total, self.max_number.total, self.amount.total),
				self.max_number.type)

		else:
			self.lifespan = GrowthResolver(*self.first.get_all())
			self.max_number = GrowthResolver(
				self.compute_maxNumber(self.period.init, self.lifespan.init, self.amount.init),
				self.compute_maxNumber(self.period.total, self.lifespan.total, self.amount.total),
				self.lifespan.type)

		print("MaxNumber: {}".format(str(self.max_number)))
		print("Lifespan: {}".format(str(self.lifespan)))

		print("Amount: {}".format(str(self.amount)))
		print("Period: {}".format(str(self.period)))
		print("Delay: {}".format(str(self.delay)))

	def computeWave(self):
		self.compute()

		message = ""


		message += self.generateWaveDescribtion("Wave #{}".format(self.init_wave),
												self.lifespan.init,
												self.max_number.init,
												self.amount.init,
												self.period.init,
												self.delay.init)

		message += self.generateWaveDescribtion("\nWave #{}".format(self.end_wave),
												self.lifespan.total,
												self.max_number.total,
												self.amount.total,
												self.period.total,
												self.delay.total)

		print(message)
		self.result.delete(0.0, END)
		self.result.insert(0.0, message)

		if self.error_message == "Errors:\n":
			self.error_message = "Success!"
		self.error.delete(0.0, END)
		self.error.insert(0.0, self.error_message)

	def generateWaveDescribtion(self, start, l, m, a, p, d):
		return "\n{}\nLifeSpan: {}\nMaxNumber: {}\nAmount:{}\nPeriod:{}\nDelay:{}".format(start, l, m, a, p, d)

	def format(self):
		self.compute()

		self.result.delete(0.0, END)
		self.result.insert(0.0, message)

	def compute_lifespan(self, period, max, npp):
		try:
			return period * (max / npp - 1)
		except:
			self.error_message += "\nUnable to compute lifespan:"
			self.error_message += "\nNPP: {}, Max:{}, Period:{}".format(npp, max, period)
			print("!Unable to compute lifespan:")
			print("!NPP: {}, Max:{}, Period:{}".format(npp, max, period))
			return "N/A"

	def compute_maxNumber(self, period, lifespan, npp):
		try:
			return npp * (lifespan / period + 1)
		except:
			self.error_message += "\nUnable to compute maxNumber:"
			self.error_message += "\nNPP: {}, LifeSpan:{}, Period:{}".format(npp, lifespan, period)
			print("!Unable to compute maxNumber:")
			print("!NPP: {}, LifeSpan:{}, Period:{}".format(npp, lifespan, period))
			return "N/A"


root = Tk()
root.title("StarSlicer Wave Configuration")

app = Application(root)

root.mainloop()
