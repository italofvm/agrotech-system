-- ============================================================
-- DADOS MOCKADOS PARA TESTES - AgroTech System
-- ============================================================
-- Credenciais de acesso:
--   admin  / senha: password  (ROLE: ADMIN)
--   usuario / senha: password  (ROLE: USER)
-- ============================================================


-- ============================================================
-- USUÁRIOS
-- ============================================================
INSERT INTO usuarios (id, login, senha, role) VALUES
  ('u100-0000-0000-0000-000000000001', 'admin',
   '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN'),
  ('u100-0000-0000-0000-000000000002', 'usuario',
   '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'USER');


-- ============================================================
-- ÁREAS
-- ============================================================
INSERT INTO areas (id, nome, descricao) VALUES
  ('a100-0000-0000-0000-000000000001', 'Estufa A',
   'Estufa principal de cultivo de hortaliças'),
  ('a100-0000-0000-0000-000000000002', 'Estufa B',
   'Estufa de mudas e plantas jovens'),
  ('a100-0000-0000-0000-000000000003', 'Campo Sul',
   'Área aberta para cultivo de grãos e oleaginosas');


-- ============================================================
-- SENSORES
-- ============================================================
INSERT INTO sensores (id, nome, localizacao, is_ativo, tipo, area_id) VALUES
  ('b100-0000-0000-0000-000000000001', 'Termômetro Estufa A-01',
   'Corredor Central - Lote A', TRUE,  'TEMPERATURA',   'a100-0000-0000-0000-000000000001'),
  ('b100-0000-0000-0000-000000000002', 'Higrômetro Solo Estufa A-01',
   'Bancada Norte - Lote A',   TRUE,  'UMIDADE_SOLO',  'a100-0000-0000-0000-000000000001'),
  ('b100-0000-0000-0000-000000000003', 'Termômetro Estufa B-01',
   'Setor Leste - Lote B',     TRUE,  'TEMPERATURA',   'a100-0000-0000-0000-000000000002'),
  ('b100-0000-0000-0000-000000000004', 'Sensor Umidade Ar B-01',
   'Setor Oeste - Lote B',     TRUE,  'UMIDADE_AR',    'a100-0000-0000-0000-000000000002'),
  ('b100-0000-0000-0000-000000000005', 'Piranômetro Campo Sul-01',
   'Ponto Central - Campo Sul', TRUE, 'LUMINOSIDADE',  'a100-0000-0000-0000-000000000003'),
  ('b100-0000-0000-0000-000000000006', 'Higrômetro Solo Campo Sul-01',
   'Bloco 3 - Campo Sul',      FALSE, 'UMIDADE_SOLO',  'a100-0000-0000-0000-000000000003');


-- ============================================================
-- HISTÓRICO DE LOCALIZAÇÃO DOS SENSORES
-- ============================================================
INSERT INTO sensores_localizacao (id, sensor_id, localizacao, data_inicio, data_fim) VALUES
  ('c100-0000-0000-0000-000000000001',
   'b100-0000-0000-0000-000000000001', 'Corredor Central - Lote A',
   '2024-01-10 08:00:00', NULL),
  ('c100-0000-0000-0000-000000000002',
   'b100-0000-0000-0000-000000000002', 'Bancada Norte - Lote A',
   '2024-01-10 08:00:00', NULL),
  ('c100-0000-0000-0000-000000000003',
   'b100-0000-0000-0000-000000000003', 'Entrada Estufa B',
   '2024-01-05 08:00:00', '2024-02-01 08:00:00'),
  ('c100-0000-0000-0000-000000000004',
   'b100-0000-0000-0000-000000000003', 'Setor Leste - Lote B',
   '2024-02-01 08:00:00', NULL),
  ('c100-0000-0000-0000-000000000005',
   'b100-0000-0000-0000-000000000005', 'Ponto Central - Campo Sul',
   '2024-01-15 08:00:00', NULL);


-- ============================================================
-- REGRAS DE ALERTA
-- ============================================================
INSERT INTO regras (id, tipo_sensor, operador, valor, mensagem, ativo) VALUES
  ('d100-0000-0000-0000-000000000001',
   'TEMPERATURA', 'MAIOR_QUE', 35.0,
   'Temperatura acima do limite permitido na estufa!', TRUE),
  ('d100-0000-0000-0000-000000000002',
   'TEMPERATURA', 'MENOR_QUE', 10.0,
   'Temperatura crítica - risco de geada!', TRUE),
  ('d100-0000-0000-0000-000000000003',
   'UMIDADE_SOLO', 'MENOR_QUE', 30.0,
   'Umidade do solo abaixo do mínimo - irrigar imediatamente!', TRUE),
  ('d100-0000-0000-0000-000000000004',
   'LUMINOSIDADE', 'MAIOR_QUE', 80000.0,
   'Luminosidade excessiva - proteção das plantas necessária!', FALSE);


-- ============================================================
-- LEITURAS  (IDs explícitos pois são referenciados nos alertas)
-- ============================================================

-- Termômetro Estufa A-01 (normal: 20-30°C | leitura 3 dispara alerta)
INSERT INTO leituras (id, sensor_id, valor, data_hora, localizacao) VALUES
  (1, 'b100-0000-0000-0000-000000000001', 24.5, '2024-06-01 06:00:00', 'Corredor Central - Lote A'),
  (2, 'b100-0000-0000-0000-000000000001', 27.3, '2024-06-01 10:00:00', 'Corredor Central - Lote A'),
  (3, 'b100-0000-0000-0000-000000000001', 36.8, '2024-06-01 14:00:00', 'Corredor Central - Lote A'),
  (4, 'b100-0000-0000-0000-000000000001', 29.1, '2024-06-01 18:00:00', 'Corredor Central - Lote A');

-- Higrômetro Solo Estufa A-01 (leitura 7 dispara alerta)
INSERT INTO leituras (id, sensor_id, valor, data_hora, localizacao) VALUES
  (5, 'b100-0000-0000-0000-000000000002', 62.0, '2024-06-01 06:00:00', 'Bancada Norte - Lote A'),
  (6, 'b100-0000-0000-0000-000000000002', 58.5, '2024-06-01 12:00:00', 'Bancada Norte - Lote A'),
  (7, 'b100-0000-0000-0000-000000000002', 25.0, '2024-06-01 18:00:00', 'Bancada Norte - Lote A');

-- Termômetro Estufa B-01
INSERT INTO leituras (id, sensor_id, valor, data_hora, localizacao) VALUES
  (8,  'b100-0000-0000-0000-000000000003', 22.1, '2024-06-01 08:00:00', 'Setor Leste - Lote B'),
  (9,  'b100-0000-0000-0000-000000000003', 26.9, '2024-06-01 14:00:00', 'Setor Leste - Lote B'),
  (10, 'b100-0000-0000-0000-000000000003', 31.2, '2024-06-01 20:00:00', 'Setor Leste - Lote B');

-- Sensor Umidade Ar Estufa B-01
INSERT INTO leituras (id, sensor_id, valor, data_hora, localizacao) VALUES
  (11, 'b100-0000-0000-0000-000000000004', 70.0, '2024-06-01 08:00:00', 'Setor Oeste - Lote B'),
  (12, 'b100-0000-0000-0000-000000000004', 65.5, '2024-06-01 14:00:00', 'Setor Oeste - Lote B');

-- Piranômetro Campo Sul-01
INSERT INTO leituras (id, sensor_id, valor, data_hora, localizacao) VALUES
  (13, 'b100-0000-0000-0000-000000000005', 45000.0, '2024-06-01 08:00:00', 'Ponto Central - Campo Sul'),
  (14, 'b100-0000-0000-0000-000000000005', 85500.0, '2024-06-01 12:00:00', 'Ponto Central - Campo Sul'),
  (15, 'b100-0000-0000-0000-000000000005', 72000.0, '2024-06-01 16:00:00', 'Ponto Central - Campo Sul'),
  (16, 'b100-0000-0000-0000-000000000005', 12000.0, '2024-06-01 19:00:00', 'Ponto Central - Campo Sul');

-- Reseta a sequência do H2 para evitar colisão de PK com os dados mock acima.
-- O próximo ID gerado automaticamente pelo scheduler será 17.
ALTER TABLE leituras ALTER COLUMN id RESTART WITH 17;

-- ============================================================
-- ALERTAS
-- ============================================================
INSERT INTO alertas (id, leituras_id, regras_id, status, data_hora) VALUES
  -- leitura 3: temperatura 36.8°C > 35°C → regra TEMPERATURA > 35 → já resolvido
  ('e100-0000-0000-0000-000000000001',
   3, 'd100-0000-0000-0000-000000000001', 'RESOLVIDO', '2024-06-01 14:05:00'),
  -- leitura 7: umidade solo 25% < 30% → regra UMIDADE_SOLO < 30 → pendente
  ('e100-0000-0000-0000-000000000002',
   7, 'd100-0000-0000-0000-000000000003', 'ATIVO', '2024-06-01 18:05:00');
