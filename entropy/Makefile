CC = gcc
CFLAGS = -Wall -Wextra

SRC = entropy.c
OBJ = $(SRC:.c=.o)
EXECUTABLE = entropy

all: $(EXECUTABLE)

$(EXECUTABLE): $(OBJ)
	$(CC) $(CFLAGS) -o $@ $^  -lm

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

clean:
	rm -f $(OBJ) $(EXECUTABLE)
