package ru.otus.project.masterPass.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.project.masterPass.domain.Entry;
import ru.otus.project.masterPass.service.EntryService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@ShellComponent
@RequiredArgsConstructor
@ShellCommandGroup(value = "Main commands:")
public class ShellSessionRunner {

    private final InputReader inputReader;
    private final EntryService service;

    private static final List<String> YES_AND_NO_OPTIONS = Arrays.asList("Y", "N");

    @ShellMethod(value = "Input new entry")
    private String create() {
        Entry entry = Entry.builder()
                .resource(inputReader.prompt("Enter resource"))
                .login(inputReader.prompt("Enter login"))
                .password(inputReader.prompt("Enter password"))
                .build();
        return service.save(entry).toString();
    }

    @ShellMethod(value = "Show stored entries")
    private String entries() {
        return service.getAll().stream().map(Entry::toString).collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Delete entry by id")
    private void delete(@ShellOption(value = "Entry id") long id) {
        Entry entry = service.getById(id);
        String lastWarn = inputReader.promptWithOptions(String.format("Do you really want to delete this entry?\n%s", entry), "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(lastWarn)) {
            service.delete(entry);
        }
    }


}