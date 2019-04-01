from tkinter import *


class GrowthResolver():
	def __init__(self, init, end, type, initVal=None, endVal=None, typeVal=None):
		self.initVal = initVal
		self.endVal = endVal
		self.typeVal = typeVal

		self.rate = None

		self.typeEntry = type
		self.endEntry = end
		self.initEntry = init

	def init(self):
		return float(self.initEntry.get()) if not initVal else initVal

	def end(self):
		return float(self.endEntry.get()) if not endVal else endVal

	def type(self):
		return bool(self.typeEntry.get()) if not typeVal else typeVal


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

		
		self.maxNumber = GrowthResolver(*self.createProperty("MaxNumber/Lifespan", 2))
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

	def get_lifespan(self):
		return maxNumber.init()

	def compute(self):

		if self.countWith == "Lifespan":
			self.maxNumber = GrowthResolver()
		else:



	def computeWave(self):
		from_wave, to_wave = int(self.from_ent.get()), int(self.to_ent.get())

		message = "Wave #{}".format(from_wave)
		message += "\nLifespan: {}\nDelay:{}\nPeriod: {}\nAmountPerSpawn:{}\nMaxNumber:{}\nHasContainer:{}\n"\
		.format(lifespan, self.delay.end(), self.period.end(), self.amountPerPeriod.end(), self.maxNumber.end(), hasContainer)

		message += "\nGrowthRate:"
		message += "\nWave #{}".format(to_wave)

		self.result.delete(0.0, END)
		self.result.insert(0.0, message)

	def format(self):
		container,from_wave,to_wave,lifespan = self.compute()

		message = "Wave #{}".format(from_wave)
		message += "\nLifespan: {}\nDelay:{}\nPeriod: {}\nAmountPerSpawn:{}\nMaxNumber:{}\n".format(t,d,p,n,m)

		self.result.delete(0.0, END)
		self.result.insert(0.0, message)

root = Tk()
root.title("StarSlicer Wave Configuration")

app = Application(root)

root.mainloop()









		