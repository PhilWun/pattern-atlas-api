
INSERT INTO public.pattern_language (id, name, uri, logo) VALUES ('82146836-1f69-4f8d-81c5-3d87a8db7663', 'Cloud Computing Patterns', 'https://patternpedia.org/patternlanguages/cloudcomputingpatterns', 'https://www.cloudcomputingpatterns.org/img/book.png');
INSERT INTO public.pattern_language (id, name, uri, logo) VALUES ('82146836-1f69-4f8d-81c5-3d87a8db7664', 'Enterprise Integration Patterns', 'https://patternpedia.org/patternlanguages/enterpriseintegrationpatterns', 'https://www.enterpriseintegrationpatterns.com/img/0321200683.gif');
INSERT INTO public.pattern_language (id, name, uri, logo) VALUES ('54b5c9fa-e25b-4ab6-a98b-0e0738323ca9', 'QC Patterns', 'https://patternpedia.org/patternLanguages/qcPatterns', null);
INSERT INTO public.pattern_schema (pattern_language_id) VALUES ('54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');

INSERT INTO public.pattern_section_schema (id, label, name, position, type, pattern_schema_pattern_language_id) VALUES (1, 'Intend', 'Intend', 0, 'any', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern_section_schema (id, label, name, position, type, pattern_schema_pattern_language_id) VALUES (2, 'Driving Question', 'Driving Question', 1, 'any', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern_section_schema (id, label, name, position, type, pattern_schema_pattern_language_id) VALUES (3, 'Icon', 'Icon', 2, 'any', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern_section_schema (id, label, name, position, type, pattern_schema_pattern_language_id) VALUES (4, 'Context', 'Context', 3, 'any', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern_section_schema (id, label, name, position, type, pattern_schema_pattern_language_id) VALUES (5, 'Solution', 'Solution', 4, 'any', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern_section_schema (id, label, name, position, type, pattern_schema_pattern_language_id) VALUES (6, 'Known uses', 'Known uses', 5, 'any', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern_section_schema (id, label, name, position, type, pattern_schema_pattern_language_id) VALUES (7, 'Next', 'Next', 6, 'any', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');

INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('82146836-1f69-4f8d-81c5-3d87a8db7665', 'TestPattern1', 'http://patternpedia.org/TestPattern1', '{"a": "b"}', '{"a": "b"}', '82146836-1f69-4f8d-81c5-3d87a8db7663');
INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('82146836-1f69-4f8d-81c5-3d87a8db7666', 'TestPattern2', 'http://patternpedia.org/TestPattern2', '{"c": "d"}', '{"a": "b"}', '82146836-1f69-4f8d-81c5-3d87a8db7663');
INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('67d9b3fa-f6d6-4c0d-a239-695c2998c71e', 'Uniform Superposition', 'https://patternpedia.org/patternLanguages/qcPatternsUniform%20Superposition', '{"Icon": "![enter image description here](http://www.cloudcomputingpatterns.org/icons/static_workload_icon.png)", "Next": "Creating uniform superposition makes use of initialization. A register in uniform superposition may be entangled. A register in uniform superposition may be input to an oracle.", "Intend": "Typically, the individual qbits of a quantum register have to be in multiple states at the same time without preferring any at these states at the beginning of the computation.", "Context": "One origin of the power of quantum algorithms stems from quantum parallelism, i.e. the ability of a quantum register to represent multiple values at the same time. This is achieved by bringing (a subset of) the qbits of a quantum register into superposition. Many algorithms assume that at the beginning this superposition is uniform, i.e. the probability of measuring any of the qbits is the same.", "Solution": "Uniform superposition is achieved by initializing the quantum register as the unit vector $|0...0\\rangle$ and applying the Hadamard tranformation afterwards:  \n$$H^{\\bigotimes n}(|0\\rangle^{n})=\\frac{1}{\\sqrt{2^n}}\\sum_ {x=0}^{2^n-1}|x\\rangle$$\nIn case the quantum register includes ancilla bits or workspace bits in addition to the computational basis, the computational basis is brought into superposition as described. The other bits may be brought into superposition themselves or not. This is achieved by using a tensor product $H^{\\bigotimes n}\\bigotimes U$, where $H^{\\bigotimes n}$ operates on the computational basis and $U$ operates on the other bits (e.g., $U=I$ in case the other bits are not brought into superposition).", "Known uses": "Most algorithms make use of uniform superposition", "Driving Question": "How can an equally weighted superposition of all possible states of the qbits of a quantum register be created?"}', '{"Icon": "![enter image description here](http://www.cloudcomputingpatterns.org/icons/static_workload_icon.png)", "Next": "Creating uniform superposition makes use of initialization. A register in uniform superposition may be entangled. A register in uniform superposition may be input to an oracle.", "Intend": "Typically, the individual qbits of a quantum register have to be in multiple states at the same time without preferring any at these states at the beginning of the computation.", "Context": "One origin of the power of quantum algorithms stems from quantum parallelism, i.e. the ability of a quantum register to represent multiple values at the same time. This is achieved by bringing (a subset of) the qbits of a quantum register into superposition. Many algorithms assume that at the beginning this superposition is uniform, i.e. the probability of measuring any of the qbits is the same.", "Solution": "Uniform superposition is achieved by initializing the quantum register as the unit vector $|0...0\\rangle$ and applying the Hadamard tranformation afterwards:  \n$$H^{\\bigotimes n}(|0\\rangle^{n})=\\frac{1}{\\sqrt{2^n}}\\sum_ {x=0}^{2^n-1}|x\\rangle$$\nIn case the quantum register includes ancilla bits or workspace bits in addition to the computational basis, the computational basis is brought into superposition as described. The other bits may be brought into superposition themselves or not. This is achieved by using a tensor product $H^{\\bigotimes n}\\bigotimes U$, where $H^{\\bigotimes n}$ operates on the computational basis and $U$ operates on the other bits (e.g., $U=I$ in case the other bits are not brought into superposition).", "Known uses": "Most algorithms make use of uniform superposition", "Driving Question": "How can an equally weighted superposition of all possible states of the qbits of a quantum register be created?"}', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('1321dd9c-52c7-4b76-a1f3-3b567c3c7780', 'PatternA', 'https://patternpedia.org/patternLanguages/qcPatternsPatternA', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here. #renderedcontent"}', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('b6a4503a-5385-4bd4-879e-b9c7998820bd', 'PatternB', 'https://patternpedia.org/patternLanguages/qcPatternsPatternB', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('13b1fbfe-369d-4e0c-8273-68d5fc1bedbe', 'Pattern4', 'https://patternpedia.org/patternLanguages/qcPatternsPattern4', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('10c55d72-b870-4a65-9c08-b5b38f0a17c8', 'PatternCD', 'https://patternpedia.org/patternLanguages/qcPatternsPatternCD', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('5243010e-82fe-4e02-a963-f736ab93c31f', 'Pattern5', 'https://patternpedia.org/patternLanguages/qcPatternsPattern5', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('43f2d8b5-467c-4ba5-afee-9340617f4c63', 'Pattern6', 'https://patternpedia.org/patternLanguages/qcPatternsPattern6', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('dbcdc0d1-e870-4858-924f-7789083827cf', 'Pattern name with a very very long title', 'https://patternpedia.org/patternLanguages/qcPatternsPattern%20name%20with%20a%20very%20very%20long%20title', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('84bc7222-cc07-4c93-b40d-41e7870d16e6', 'PatternE', 'https://patternpedia.org/patternLanguages/qcPatternsPatternE', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.pattern (id, name, uri, content, rendered_content, pattern_language_id) VALUES ('e633a551-e033-4c23-b3c3-2cffda4b3cd5', 'Pattern DF', 'https://patternpedia.org/patternLanguages/qcPatternsPattern%20DF', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '{"Icon": "Enter your input for this section here.", "Next": "Enter your input for this section here.", "Intend": "Enter your input for this section here.", "Context": "Enter your input for this section here.", "Solution": "Enter your input for this section here.", "Known uses": "Enter your input for this section here.", "Driving Question": "Enter your input for this section here."}', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');

INSERT INTO public.pattern_view (id, name, uri) VALUES ('82146836-1f69-4f8d-81c5-3d87a8db7690', 'TestView', 'https://patternpedia.org/patternViews/TestView');
INSERT INTO public.pattern_view (id, name, uri) VALUES ('82146836-1f69-4f8d-81c5-3d87a8db7691', 'TestView2', 'https://patternpedia.org/patternViews/TestView2');
INSERT INTO public.pattern_view_pattern (pattern_view_id, pattern_id) VALUES ('82146836-1f69-4f8d-81c5-3d87a8db7690', '82146836-1f69-4f8d-81c5-3d87a8db7666');
INSERT INTO public.pattern_view_pattern (pattern_view_id, pattern_id) VALUES ('82146836-1f69-4f8d-81c5-3d87a8db7691', '82146836-1f69-4f8d-81c5-3d87a8db7666');

INSERT INTO public.directed_edge (id, description, type, pattern_language_id, source_id, target_id) VALUES ('ffb2e8a1-14f1-4e29-a6aa-284a5bff40f6', 'null', 'isUsedBefore', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9', '1321dd9c-52c7-4b76-a1f3-3b567c3c7780', 'b6a4503a-5385-4bd4-879e-b9c7998820bd');
INSERT INTO public.directed_edge (id, description, type, pattern_language_id, source_id, target_id) VALUES ('79f256f3-5de1-4b55-b692-872dd99d89e7', 'null', 'isUsedAfter', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9', '67d9b3fa-f6d6-4c0d-a239-695c2998c71e', '1321dd9c-52c7-4b76-a1f3-3b567c3c7780');
INSERT INTO public.directed_edge (id, description, type, pattern_language_id, source_id, target_id) VALUES ('38be2910-aa36-4310-8888-8079387754c2', 'null', 'isUsedAfter', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9', '10c55d72-b870-4a65-9c08-b5b38f0a17c8', 'b6a4503a-5385-4bd4-879e-b9c7998820bd');
INSERT INTO public.directed_edge (id, description, type, pattern_language_id, source_id, target_id) VALUES ('8d60f611-4fae-4d67-ae5a-3c90d7d8d301', 'null', 'isRelatedTo', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9', '13b1fbfe-369d-4e0c-8273-68d5fc1bedbe', '10c55d72-b870-4a65-9c08-b5b38f0a17c8');
INSERT INTO public.directed_edge (id, description, type, pattern_language_id, source_id, target_id) VALUES ('bbbb0014-c31a-443c-ae62-4a1b763fff14', 'null', 'isRelatedTo', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9', '84bc7222-cc07-4c93-b40d-41e7870d16e6', 'b6a4503a-5385-4bd4-879e-b9c7998820bd');
INSERT INTO public.directed_edge (id, description, type, pattern_language_id, source_id, target_id) VALUES ('e5beb9f7-4452-43ab-bb0e-e09605bb9a5d', 'null', 'isRelatedTo', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9', '84bc7222-cc07-4c93-b40d-41e7870d16e6', 'e633a551-e033-4c23-b3c3-2cffda4b3cd5');

INSERT INTO public.undirected_edge (id, description, type, p1_id, p2_id, pattern_language_id) VALUES ('9b100c69-7d89-4f18-882d-1a74068a3301', 'null', 'isRelatedTo', 'b6a4503a-5385-4bd4-879e-b9c7998820bd', '67d9b3fa-f6d6-4c0d-a239-695c2998c71e', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');
INSERT INTO public.undirected_edge (id, description, type, p1_id, p2_id, pattern_language_id) VALUES ('3267abbf-1435-436f-865e-278cd200038e', 'null', 'isUsedAfter', '10c55d72-b870-4a65-9c08-b5b38f0a17c8', '84bc7222-cc07-4c93-b40d-41e7870d16e6', '54b5c9fa-e25b-4ab6-a98b-0e0738323ca9');

