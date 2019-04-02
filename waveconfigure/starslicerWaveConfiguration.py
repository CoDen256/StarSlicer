from tkinter import *
from math import *


class GrowthResolver():
	def __init__(self, init=None, end=None, type=None, initVal=-1, endVal=-1, typeVal=-1):
		self.initVal = initVal
		self.endVal = endVal
		self.typeVal = typeVal

		self.rate = None

		self.typeEntry = type
		self.endEntry = end
		self.initEntry = init

	def init(self):
		try :
			return float(self.initEntry.get())
		except:
			return self.initVal

	def end(self):
		try:
			return float(self.endEntry.get())
		except:
			return self.endVal

	def type(self):
		try :
			return self.typeEntry.get()
		except:
			return self.typeVal


class Application(Frame):
	"""docstring for Application"""
	def __init__(self, master):
		super(Application, self).__init__(master)

		self.grid()
		self.create_widgets()


	def createProperty(self, text, r, c=0):
		types = ["Exponential","Polynomial"]
		Label(self, text = text).grid(row = r, column = c, sticky = W)
		initEntry = Entry(self)
		initEntry.grid(row = r, column = c+1, sticky = W)
		Label(self, text = "  to").grid(row = r, column = c+2, sticky = W)
		endEntry = Entry(self)
		endEntry.grid(row =r,column =  c+3, sticky = W)

		typeEntry = StringVar()
		typeEntry.set(None)

		self.createRadioButtons("GrowthType:", typeEntry, types, r, c+4)

		return initEntry, endEntry, typeEntry

	def createRadioButtons(self, text, var, options, r, c):
		Label(self, text=text).grid(row=r, column=c, sticky=W)
		for o in options:
			Radiobutton(self,text=o, variable=var, value = o).grid(row=r,column=c+1,sticky=W)
			c += 1

	def create_widgets(self):


		self.countWith = StringVar()
		self.countWith.set(None)
		self.createRadioButtons("Compute By",self.countWith, ['MaxNumber', 'Lifespan'], 1, 0)


		self.temp = GrowthResolver(*self.createProperty("MaxNumber/Lifespan", 2))
		self.amountPerPeriod = GrowthResolver(*self.createProperty("Amount per spawn", 3))
		self.period = GrowthResolver(*self.createProperty("Period", 4))
		self.delay = GrowthResolver(*self.createProperty("Delay",5))

		Label(self,
			  text = "From Wave: ").grid(row = 6, column = 0, sticky = W)
		self.from_ent = Entry(self)
		self.from_ent.grid(row = 6, column = 1, sticky = W)

		Label(self,
			  text = "To Wave: ").grid(row = 6, column = 2, sticky = W)
		self.to_ent = Entry(self)
		self.to_ent.grid(row = 6, column = 3, sticky = W)


		self.hasContainer = BooleanVar()
		Checkbutton(self, text = "Has Container",variable = self.hasContainer).grid(row = 7, column = 1, sticky = W)

		Button(self, text = "Compute",command = self.computeWave).grid(row = 8, column = 0, sticky = W)
		Button(self,text = "Format",command = self.format).grid(row = 8, column = 1, sticky = W)

		self.result = Text(self, width = 100, height = 30, wrap = WORD)
		self.result.grid(row = 9, column = 0, columnspan = 4)

	def countLifespan(self, m, a, p):
		return p*(m/a - 1)

	def countMaxNumber(self, t, a, p):
		return a*(t/p + 1)


	def compute(self):
		if self.countWith.get() == "Lifespan":
			print("Lifespaning")
			self.maxNumber = GrowthResolver(initVal = self.countMaxNumber(self.temp.init(), self.amountPerPeriod.init(), self.period.init()),
											endVal = self.countMaxNumber(self.temp.end(), self.amountPerPeriod.end(), self.period.end()),
											typeVal = self.temp.type())
			self.lifeSpan = self.temp
		else:
			print("Maxnumbering")
			self.maxNumber = self.temp
			self.lifeSpan = GrowthResolver(initVal = self.countLifespan(self.temp.init(), self.amountPerPeriod.init(), self.period.init()),
			 								endVal = self.countLifespan(self.temp.end(), self.amountPerPeriod.end(), self.period.end()),
											typeVal = self.temp.type())
			print(self.maxNumber.init())



	def computeExpRate(self, init, end, num):
		return (end/init)**(1/num)

	def computePolyRate(self, init, end, num):
		return (end-init)/num

	def computeRate(self, gr, n):
		if gr.type() == "Exponential":
			return self.computeExpRate(gr.init(), gr.end(), n)
		if gr.type() == "Polynomial":
			return self.computePolyRate(gr.init(), gr.end(), n)

	def computeWave(self):
		self.compute()
		try:
			from_wave, to_wave = int(self.from_ent.get()), int(self.to_ent.get())
		except:
			from_wave, to_wave = -1, -1

		message = "Wave #{}".format(from_wave)
		message += "\nLifespan: {} - {}\nDelay:{} - {}\nPeriod: {}- {}\nAmountPerSpawn:{} - {}\nMaxNumber:{} - {}\nHasContainer:{}\n"\
		.format(self.lifeSpan.end(), self.lifeSpan.type(), self.delay.end(), self.delay.type(),
		self.period.end(), self.period.type(), self.amountPerPeriod.end(), self.amountPerPeriod.type(),
		self.maxNumber.end(), self.maxNumber.type(), self.hasContainer.get())

		message += "\n\nWave #{}".format(to_wave)
		message += "\nLifespan: {}\nDelay:{}\nPeriod: {}\nAmountPerSpawn:{}\nMaxNumber:{}\nHasContainer:{}\n"\
		.format(self.lifeSpan.init(), self.delay.init(), self.period.init(), self.amountPerPeriod.init(), self.maxNumber.init(), self.hasContainer.get())

		print()
		message += "\nGrowthRate:"
		f = from_wave
		message += "\nLifespan: {}\nDelay:{}\nPeriod: {}\nAmountPerSpawn:{}\nMaxNumber:{}\n"\
		.format(self.computeRate(self.lifeSpan, f),self.computeRate(self.delay, f),
				self.computeRate(self.period, f),self.computeRate(self.amountPerPeriod, f),self.computeRate(self.maxNumber, f))



		self.result.delete(0.0, END)
		self.result.insert(0.0, message)

	def format(self):
		self.compute()

		message = "Wave #{}".format(from_wave)
		message += "\nLifespan: {}\nDelay:{}\nPeriod: {}\nAmountPerSpawn:{}\nMaxNumber:{}\n".format(t,d,p,n,m)

		self.result.delete(0.0, END)
		self.result.insert(0.0, message)

root = Tk()
root.title("StarSlicer Wave Configuration")

app = Application(root)

root.mainloop()
